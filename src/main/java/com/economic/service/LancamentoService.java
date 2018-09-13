package com.economic.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economic.dto.LancamentoPessoa;
import com.economic.model.Lancamento;
import com.economic.model.Pessoa;
import com.economic.repository.LancamentoRepository;
import com.economic.repository.PessoaRepository;
import com.economic.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		validarPessoa(lancamento);
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalvo = this.buscarLancamento(id);
		
		if(!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	private void validarPessoa(Lancamento lancamento) {
		
		Pessoa pessoa = lancamento.getPessoa();
		
		if(pessoa.getId() != null) {
			pessoa = pessoaRepository.findOne(pessoa.getId());
		}
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	private Lancamento buscarLancamento(Long id) {
		Lancamento lancamento = lancamentoRepository.findOne(id);
		
		if (lancamento == null) {
			throw new IllegalArgumentException();
		}

		return lancamento;
	}
	
	public void remover(Long id) {
		lancamentoRepository.delete(id);
	}
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		
		List<LancamentoPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DATA_INICIO", Date.valueOf(inicio));
		parametros.put("DATA_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/report/lancamento_por_pessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
}
