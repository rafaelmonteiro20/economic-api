package com.economic.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.economic.model.Categoria;
import com.economic.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriasResource {

	@Autowired
	private CategoriaRepository repository;
	
	@GetMapping
	public List<Categoria> listar() {
		return repository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria) {
		categoria = repository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
											 .buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).body(categoria);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Categoria categoria = repository.findOne(id);
		return categoria == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
	}
	
}