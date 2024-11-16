package pj.powerr.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private String SECRET_KEY = "secret";

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractUsername(String token) {
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        }
        catch(JWTVerificationException e){
            return null;
        }
    }

    private boolean isTokenExpiered(String token) {
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getExpiresAt().before(new Date());
        }
        catch(JWTVerificationException e){
            return true;
        }
    }

    private boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername != null && extractedUsername.equals(username) && !isTokenExpiered(token);
    }
}
