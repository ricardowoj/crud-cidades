package br.edu.utfpr.cp.espjava.crudcidades.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.cp.espjava.crudcidades.domain.Usuario;
import br.edu.utfpr.cp.espjava.crudcidades.repository.UsuarioRepository;

@Service
public class UsuarioDetailService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private Boolean localizado = false;

	public UsuarioDetailService(final UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException{
		var usuario = usuarioRepository.findByNomeContaining(nome);
		if (usuario == null) {
			System.out.println("Usuário não localizado...");
			throw new UsernameNotFoundException("Usuário não Encontrado!");
		} else {
			System.out.println("Usuário Localizado...");
			return usuario;
		}
	}

	public Boolean getLocalizado() {
		return localizado;
	}

	public void setLocalizado(Boolean localizado) {
		this.localizado = localizado;
	}
}
