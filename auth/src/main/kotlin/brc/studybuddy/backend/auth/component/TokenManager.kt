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

const val accessTokenTypeName: String = "isAccessToken"

typealias Tokens = Pair<String, String>

@Component
class TokenManager(
    @Value("\${secrets.access.ttl}") val accessTokenTimeToLive: Long,
    @Value("\${secrets.refresh.ttl}") val refreshTokenTimeToLive: Long,
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
     * Generate access and refresh token for the given user, with the given time to live (expressed in seconds)
     */
    fun generateTokens(user: User): Tokens {
        val now = Instant.now()
        val accessTokenExpire = now.plusSeconds(accessTokenTimeToLive)
        val refreshTokenExpire = accessTokenExpire.plusSeconds(refreshTokenTimeToLive)

        val tokenBuilder = signer.setSubject(user.id.toString())
            .setIssuedAt(Date.from(now))

        return Pair(
            tokenBuilder.claim(accessTokenTypeName, true)
                .setExpiration(Date.from(accessTokenExpire))
                .compact(),
            tokenBuilder.claim(accessTokenTypeName, false)
                .setExpiration(Date.from(refreshTokenExpire))
                .compact()
        )
    }

}