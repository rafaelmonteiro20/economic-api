package com.economic.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.economic.event.RecursoCriadoEvent;
import com.economic.model.Categoria;
import com.economic.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriasResource {

	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
//	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public List<Categoria> listar() {
		return repository.findAll();
	}
	
	@PostMapping
//	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
	public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		categoria = repository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
	}
	
	@GetMapping("/{id}")
//	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Categoria categoria = repository.findOne(id);
		return categoria == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		System.out.println("Removendo categoria: " + id);
	}
	
}