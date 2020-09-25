package pl.szymanski.paker.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.szymanski.paker.security.services.UserDetails;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${paker.app.jwtSecret}")
	private String jwtSecret;

	@Value("${paker.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Błędna sygnatura tokena: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Błędny token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Token przedawniony: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Token niewspierany: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Token pusty: {}", e.getMessage());
		}

		return false;
	}
}