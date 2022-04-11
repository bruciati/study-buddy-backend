package brc.studybuddy.backend.auth.component

import brc.studybuddy.model.User
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class TokenManager(
    @Value("\${secrets.ttl}") val timeToLive: Long,
    @Value("\${secrets.key}") val secretKey: String
) {

    val signer: JwtBuilder =
        Jwts.builder()
            .signWith(
                SecretKeySpec(
                    Base64.getDecoder().decode(secretKey),
                    SignatureAlgorithm.HS256.jcaName
                )
            )

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