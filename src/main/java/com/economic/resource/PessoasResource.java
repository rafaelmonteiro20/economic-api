package com.economic.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.economic.event.RecursoCriadoEvent;
import com.economic.model.Pessoa;
import com.economic.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoasResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		pessoa = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaRepository.findOne(id);
		return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoa);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		pessoaRepository.delete(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
		Pessoa existente = pessoaRepository.findOne(id);
		
		if(existente == null)
			throw new EmptyResultDataAccessException(1);
		
		BeanUtils.copyProperties(pessoa, existente, "id");
		
		pessoaRepository.save(existente);
		return ResponseEntity.ok(existente);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatus(@PathVariable Long id, @RequestBody Boolean ativo) {
		Pessoa pessoa = pessoaRepository.findOne(id);
		
		if(pessoa == null)
			throw new EmptyResultDataAccessException(1);
		
		pessoa.setAtivo(ativo);
		pessoaRepository.save(pessoa);
	}
	
}