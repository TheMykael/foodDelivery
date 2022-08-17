package br.com.zpto.foods.foodDelivery.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.zpto.foods.foodDelivery.service.UsuarioJwtService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	 private final UsuarioJwtService usuarioJwtService;
	    private final JwtTokenUtil jwtTokenUtil;

	    public JwtRequestFilter(UsuarioJwtService usuarioJwtService, JwtTokenUtil jwtTokenUtil) {
	        this.usuarioJwtService = usuarioJwtService;
	        this.jwtTokenUtil = jwtTokenUtil;
	    }

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {

	        final String requestTokenHeader = request.getHeader("Authorization");
	        if (StringUtils.startsWith(requestTokenHeader,"Bearer ")) {
	            String jwtToken = requestTokenHeader.substring(7);
	            try {
	                String username = jwtTokenUtil.obterUsernamePorToken(jwtToken);
	                if (StringUtils.isNotEmpty(username)
	                        && null == SecurityContextHolder.getContext().getAuthentication()) {
	                    UserDetails userDetails = usuarioJwtService.loadUserByUsername(username);
	                    if (jwtTokenUtil.validarToken(jwtToken, userDetails)) {
	                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                                new UsernamePasswordAuthenticationToken(
	                                        userDetails, null, userDetails.getAuthorities());
	                        usernamePasswordAuthenticationToken
	                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                        SecurityContextHolder.getContext()
	                                .setAuthentication(usernamePasswordAuthenticationToken);
	                    }
	                }
	            } catch (ExpiredJwtException e) {
	                logger.error("O token JWT está expirado");
	            } catch (Exception e) {
	                logger.error("Ocorreu um erro ao realizar a autenticação via JWT", e);
	            }
	        } else {
	            logger.warn("O token JWT não inicia com a string Bearer");
	        }
	        chain.doFilter(request, response);
	    }

}
