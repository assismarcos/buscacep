package com.disciolli.buscacep.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.disciolli.buscacep.model.Usuario;
import com.disciolli.buscacep.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

  private UsuarioRepository usuarioRepository;
  
  public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
	this.usuarioRepository = usuarioRepository;
}

  @Override
  public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
    Usuario u = usuarioRepository.findByNome(nome);

    if (u == null) {
      throw new UsernameNotFoundException(null);
    }

    return org.springframework.security.core.userdetails.User
        .withUsername(nome)
        .password(u.getSenha())
        .authorities(u.getFuncao())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }

}
