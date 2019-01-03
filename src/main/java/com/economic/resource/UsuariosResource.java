package com.economic.resource;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.model.Usuario;
import com.economic.repository.UsuarioRepository;
import com.economic.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosResource {

	private UsuarioService usuarioService;
	
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public List<Usuario> pesquisar() {
		return usuarioRepository.findAll();
	}
	
}
