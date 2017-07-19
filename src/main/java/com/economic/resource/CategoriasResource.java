package com.economic.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}