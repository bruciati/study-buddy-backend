package brc.studybuddy.backend.auth.service

import brc.studybuddy.backend.auth.component.FacebookWebClient
import brc.studybuddy.backend.auth.component.TokenManager
import brc.studybuddy.backend.auth.component.Tokens
import brc.studybuddy.backend.auth.component.UsersWebClient
import brc.studybuddy.backend.auth.model.AuthError
import brc.studybuddy.backend.auth.model.FacebookError
import brc.studybuddy.backend.auth.model.FacebookSuccess
import brc.studybuddy.input.UserInput
import brc.studybuddy.model.User
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService {

    @Autowired
    lateinit var facebookWebClient: FacebookWebClient

    @Autowired
    lateinit var usersWebClient: UsersWebClient

    @Autowired
    lateinit var tokenManager: TokenManager

    /*
    * Authenticate the User using the given credentials
    * @returns:
    *   - A Mono of pair that contains the access token and the refresh token of the given user
    *   - A Mono containing an error that encode the cause
    */
    fun authenticate(user: UserInput): Mono<Tokens> =
        validateUserInput(user)
            .flatMap {
                when (it.authType) {
                    User.AuthType.FACEBOOK -> facebookAuthentication(it)
                    User.AuthType.PASSWORD -> emailAuthentication(it)
                    else -> Mono.error(AuthError(HttpStatus.SC_BAD_REQUEST, "Login type not allowed"))
                }
            }
            .map { tokenManager.generateTokens(it.id) }

    /*
    * Validate the given UserInput by returning an error in case it's invalid
    * @returns:
    *   - A Mono<User> containing the validated user
    *   - A Mono<AuthError> in case it is not valid
    */
    private fun validateUserInput(user: UserInput): Mono<UserInput> {
        return if (user.email != null && user.authValue != null && user.authType != null)
            Mono.just(user)
        else
            Mono.error(AuthError(HttpStatus.SC_BAD_REQUEST, "The given UserInput is invalid"))
    }

    /*
    *  Perform the authentication using email and password
    */
    private fun emailAuthentication(user: UserInput): Mono<User> =
        Mono.just(user)
            .flatMap { u ->
                usersWebClient.getUserByEmail(u.email)
                    .filter { it.authValue == u.authValue }
            }
            .switchIfEmpty(Mono.error(AuthError(HttpStatus.SC_UNAUTHORIZED, "Incorrect credentials")))

    /*
    * Perform the authentication using the provided Facebook token
    * It checks if the received Facebook token is valid by calling
    * Facebook's Graph API, if it is valid, then it tries to fetch the
    * corresponding user from the Users microservice. If no users are
    * found, then it sends a request to create such user.
    * @returns:
    *   - A Mono<User> containing the authenticated user associated
    *     with the given token
    *   - A Mono<AuthError> enconding the encountered error
    */
    fun facebookAuthentication(user: UserInput): Mono<User> =
        facebookWebClient.getTokenInfo(user.authValue)
            .map { response ->
                when (response.data) {
                    is FacebookSuccess -> response.data.userId
                    is FacebookError -> throw AuthError(response.data.error.code, response.data.error.message)
                    else -> throw Error("An unexpected error occurred while trying to fetch Facebook token data")
                }
            }
            .flatMap {
                usersWebClient
                    .getUserByEmail(user.email)
                    .switchIfEmpty(
                        usersWebClient.insertUser(
                            UserInput(
                                user.email,
                                User.AuthType.FACEBOOK,
                                it.toString()
                            )
                        )
                    )
            }

    fun refresh(refreshToken: String): Mono<Tokens> =
        Mono.just(refreshToken)
            .map(tokenManager::validateRefreshToken)
            .onErrorMap { e -> AuthError(HttpStatus.SC_BAD_REQUEST, e.message!!) }
            .map(tokenManager::generateTokens)

}