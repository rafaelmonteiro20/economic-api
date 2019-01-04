package com.economic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.economic.model.Usuario;
import com.economic.repository.UsuarioRepository;
import com.economic.repository.filter.UsuarioFilter;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		return usuario;
	}
	
	public List<Usuario> pesquisar(UsuarioFilter filtro) {
		
		Usuario usuario = new Usuario();
		usuario.setNome(filtro.getNome());
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("nome", match -> match.ignoreCase())
				.withMatcher("nome", match -> match.contains());
		
		Example<Usuario> exemplo = Example.of(usuario, matcher);
		return usuarioRepository.findAll(exemplo);
	}
	
}
