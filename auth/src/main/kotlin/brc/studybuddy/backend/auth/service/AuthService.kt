package brc.studybuddy.backend.auth.service

import brc.studybuddy.backend.auth.component.TokenManager
import brc.studybuddy.backend.auth.model.AuthError
import brc.studybuddy.backend.auth.repository.UserRepository
import brc.studybuddy.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService {

    @Autowired
    lateinit var userRepository: UserRepository

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
            .flatMap { u -> userRepository.findFirstByEmailAndLoginValue(u.email, u.authValue) }
            .switchIfEmpty(Mono.error(AuthError(401, "Incorrect credentials")))

    fun facebookAuthentication(user: User): Mono<User> =
        Mono.just(user)
            .flatMap { u -> userRepository.findFirstByEmailAndLoginValue(u.email, u.authValue) }
            .filter { it.authValue == user.authValue }
            .switchIfEmpty(checkFacebookToken(user))

    /*
     * Check the facebook token, if valid, save it into the repository
     */
    fun checkFacebookToken(user: User): Mono<User> = Mono.just(user)
}