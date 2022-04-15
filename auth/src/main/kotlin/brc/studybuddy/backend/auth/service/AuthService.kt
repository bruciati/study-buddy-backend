package brc.studybuddy.backend.auth.service

import brc.studybuddy.backend.auth.component.FacebookWebClient
import brc.studybuddy.backend.auth.component.TokenManager
import brc.studybuddy.backend.auth.component.UsersWebClient
import brc.studybuddy.backend.auth.model.AuthError
import brc.studybuddy.backend.auth.model.FacebookError
import brc.studybuddy.backend.auth.model.FacebookSuccess
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


/*
 * TODO: Change hardcoded reponse statuses to HTPPStatuses
 */
@Service
class AuthService {

    @Autowired
    lateinit var facebookWebClient: FacebookWebClient

    @Autowired
    lateinit var usersWebClient: UsersWebClient

    @Autowired
    lateinit var tokenManager: TokenManager

    fun authenticate(user: User): Mono<String> =
        Mono.just(user)
            .flatMap {
                when (it.authType) {
                    User.AuthType.FACEBOOK -> facebookAuthentication(it)
                    User.AuthType.PASSWORD -> emailAuthentication(it)
                    else -> Mono.error(AuthError(401, "Login type not allowed"))
                }
            }
            .map(tokenManager::generateToken)

    fun emailAuthentication(user: User): Mono<User> =
        Mono.just(user)
            .flatMap { u ->
                usersWebClient.getUserByEmail(u.email)
                    .filter { it.authValue == u.authValue }
            }
            .switchIfEmpty(Mono.error(AuthError(401, "Incorrect credentials")))

    fun facebookAuthentication(user: User): Mono<User> =
        /*
         * Check if the received Facebook token is valid by calling the Facebook API
         * If the user is valid, returns a the userId associated with the token
         */
        facebookWebClient.getTokenInfo(user.authValue)
            .map { response ->
                when (response.data) {
                    is FacebookSuccess -> response.data.userId
                    is FacebookError -> throw AuthError(401, response.data.error.message)
                    else -> {
                        throw Error("An unexpected error occurred while trying to fetch Facebook token data")
                    }
                }
            }
            .flatMap {
                usersWebClient
                    .getUserByEmail(user.email)
                    //.onErrorResume.. (we don't know already how this function will behave
                    .switchIfEmpty(usersWebClient.insertFacebookUser(user.email, it))
            }
}