package com.economic.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.event.RecursoCriadoEvent;
import com.economic.model.Usuario;
import com.economic.repository.filter.UsuarioFilter;
import com.economic.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosResource {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Usuario> pesquisar(UsuarioFilter filtro) {
		return usuarioService.pesquisar(filtro);
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		usuario = usuarioService.salvar(usuario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
	
}
