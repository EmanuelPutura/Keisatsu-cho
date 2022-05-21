package ubb.keisatsu.cms.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import ubb.keisatsu.cms.model.entities.UserRole
import java.util.*
import javax.crypto.SecretKey



@Component
class JwtTokenUtil {

    private final val signatureAlgorithm = SignatureAlgorithm.HS256
    val secretKey: SecretKey = Keys.secretKeyFor(signatureAlgorithm)
//    var secretString: String = Encoders.BASE64.encode(secretKey.getEncoded())


    fun createJWT(id: String?, issuer: String?, subject: String?, role: UserRole, ttlSecs: Long): String {

        val nowMillis = System.currentTimeMillis()
        val now = Date(nowMillis)

        //Let's set the JWT Claims
        val builder: JwtBuilder = Jwts.builder().setId(id)
            .setIssuedAt(now)
            .setSubject(subject)
            .setIssuer(issuer)
            .claim("role", role.authority)
            .signWith(secretKey, signatureAlgorithm)

        //if it has been specified, let's add the expiration
        if (ttlSecs > 0) {
            val expMillis = nowMillis + ttlSecs * 1000
            val exp = Date(expMillis)
            builder.setExpiration(exp)
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact()
    }

    fun validate(jwt: String): Boolean {
        return try {
            decodeJWT(jwt)
            true
        } catch(e: Exception) {
            false
        }
    }

    fun decodeJWT(jwt: String): Claims {
        //This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(jwt).body
    }
}