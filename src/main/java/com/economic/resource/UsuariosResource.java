package com.economic.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.model.Usuario;
import com.economic.repository.filter.UsuarioFilter;
import com.economic.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosResource {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> pesquisar(UsuarioFilter filtro) {
		return usuarioService.pesquisar(filtro);
	}
	
}
