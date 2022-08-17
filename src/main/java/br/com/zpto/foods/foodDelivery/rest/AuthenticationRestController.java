package br.com.zpto.foods.foodDelivery.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.zpto.foods.foodDelivery.dao.UsuarioDAO;
import br.com.zpto.foods.foodDelivery.entity.Usuario;
import br.com.zpto.foods.foodDelivery.service.UsuarioJwtService;
import br.com.zpto.foods.foodDelivery.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {
	
	protected final Log logger = LogFactory.getLog(getClass());

    final UsuarioDAO usuarioDAO;
    final AuthenticationManager authenticationManager;
    final UsuarioJwtService usuarioJwtService;
    final JwtTokenUtil jwtTokenUtil;

    public AuthenticationRestController(UsuarioDAO usuarioDAO, AuthenticationManager authenticationManager,
                                    UsuarioJwtService usuarioJwtService, JwtTokenUtil jwtTokenUtil) {
        this.usuarioDAO = usuarioDAO;
        this.authenticationManager = authenticationManager;
        this.usuarioJwtService = usuarioJwtService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestParam("username") String username,
                                       @RequestParam("password") String password) {
        Map<String, Object> mapaDeResposta = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username
                    , password));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = usuarioJwtService.loadUserByUsername(username);
                String token = jwtTokenUtil.gerarToken(userDetails);
                mapaDeResposta.put("erro", false);
                mapaDeResposta.put("mensagem", "Login efetuado com sucesso");
                mapaDeResposta.put("token", token);
                return ResponseEntity.ok(mapaDeResposta);
            }
            
			mapaDeResposta.put("erro", true);
			mapaDeResposta.put("mensagem", "Credenciais inválidas");
			return ResponseEntity.status(401).body(mapaDeResposta);
        } catch (DisabledException e) {
            e.printStackTrace();
            mapaDeResposta.put("erro", true);
            mapaDeResposta.put("mensagem", "Este usuário está desativado");
            return ResponseEntity.status(500).body(mapaDeResposta);
        } catch (BadCredentialsException e) {
            mapaDeResposta.put("erro", true);
            mapaDeResposta.put("mensagem", "Credenciais inválidas");
            return ResponseEntity.status(401).body(mapaDeResposta);
        } catch (Exception e) {
            e.printStackTrace();
            mapaDeResposta.put("erro", true);
            mapaDeResposta.put("mensagem", "Ocorreu um erro, contate o administrador do sistema");
            return ResponseEntity.status(500).body(mapaDeResposta);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestParam("nome") String nome,
                                      @RequestParam("username") String username, @RequestParam("password") String password) {
        Map<String, Object> responseMap = new HashMap<>();
        Usuario user = new Usuario();
        user.setNome(nome);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole("USER");
        user.setUsername(username);
        UserDetails userDetails = usuarioJwtService.loadUserByUsername(username);
        String token = jwtTokenUtil.gerarToken(userDetails);
        usuarioDAO.save(user);
        responseMap.put("erro", false);
        responseMap.put("username", username);
        responseMap.put("mensagem", "Conta criada com sucesso!");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }

}
