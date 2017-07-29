package com.economic.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.economic.event.RecursoCriadoEvent;
import com.economic.model.Lancamento;
import com.economic.repository.LancamentoRepository;
import com.economic.repository.filter.LancamentoFilter;
import com.economic.service.LancamentoService;
import com.economic.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.pesquisar(lancamentoFilter);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> buscarPorId(@PathVariable Long id) {
		Lancamento lancamento = lancamentoRepository.findOne(id);
		return lancamento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(lancamento);
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		try {
			lancamento = lancamentoService.salvar(lancamento);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getId()));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
		} catch (PessoaInexistenteOuInativaException e) {
			String mensagem = messageSource.getMessage("pessoa.inexistente-ou-inativa", 
					null, LocaleContextHolder.getLocale());
			
			return ResponseEntity.badRequest().body(mensagem);
		}
	}
}