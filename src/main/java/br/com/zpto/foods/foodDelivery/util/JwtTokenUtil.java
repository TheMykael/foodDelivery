package br.com.zpto.foods.foodDelivery.util;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -5050709804638756822L;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String obterUsernamePorToken(String token) {
        return obterClaimPorToken(token, Claims::getSubject);
    }

    public Date obterDataExpiracaoPorToken(String token) {
        return obterClaimPorToken(token, Claims::getExpiration);
    }

    public <T> T obterClaimPorToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = obterTodasClaimsPorToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims obterTodasClaimsPorToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpirado(String token) {
        final Date expiration = obterDataExpiracaoPorToken(token);
        return expiration.before(new Date());
    }

    public String gerarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(key).compact();
    }

    public Boolean validarToken(String token, UserDetails userDetails) {
        final String username = obterUsernamePorToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }

}
