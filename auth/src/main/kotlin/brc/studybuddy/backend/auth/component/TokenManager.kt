package brc.studybuddy.backend.auth.component

import brc.studybuddy.model.User
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.spec.SecretKeySpec

@Component
class TokenManager {

    @Value("\${secrets.ttl}")
    var timeToLive: Long = 0

    @Value("\${secrets.key}")
    lateinit var secretKey: String

    lateinit var signer: JwtBuilder

    /*
     * Since JWT depends on the private Key (which is Autowired) we're not exactly sure when the container
     * system will eventually autowire that parameter. We use @PostConstruct to be sure that such method
     * will be executed after the bean creation.
     */
    @PostConstruct
    fun finalizeConstruction() {
        val key = SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.jcaName)
        signer = Jwts.builder().signWith(key)
    }

    /*
     * Generate a new access token for the given user, with the given time to live (expressed in seconds)
     */
    fun generateToken(user: User): String {
        val now = Instant.now()
        val expire = now.plusSeconds(timeToLive)
        return signer.setSubject(user.id.toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expire))
            .compact()
    }
}