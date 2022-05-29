package br.edu.utfpr.cp.espjava.crudcidades.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.cp.espjava.crudcidades.repository.UsuarioRepository;

@Service
public class UsuarioDetailService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
			logger.info("usuario nao localizado :: {}",  nome);
			throw new UsernameNotFoundException("Usuário não Encontrado!");
		} 

		logger.info("usuario logado :: {}",  usuario.getNome());
		return usuario;
	}

	public Boolean getLocalizado() {
		return localizado;
	}

	public void setLocalizado(Boolean localizado) {
		this.localizado = localizado;
	}
}
