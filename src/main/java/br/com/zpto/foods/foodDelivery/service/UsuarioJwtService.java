package br.com.zpto.foods.foodDelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.zpto.foods.foodDelivery.dao.UsuarioDAO;
import br.com.zpto.foods.foodDelivery.entity.Usuario;

@Service
public class UsuarioJwtService implements UserDetailsService {

	final UsuarioDAO usuarioDAO;

    public UsuarioJwtService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByUsername(username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER"));
        return new User(usuario.getUsername(), usuario.getPassword(), authorityList);
    }

}
