package com.economic.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.economic.config.property.EconomicApiProperty;
import com.economic.mail.Mailer;
import com.economic.model.Lancamento;
import com.economic.model.Usuario;
import com.economic.repository.LancamentoRepository;
import com.economic.repository.UsuarioRepository;
import com.economic.service.Notificador;

@Component
public class NotificadorLancamentosVencidos implements Notificador {

	private static final String PERMISSAO = "ROLE_PESQUISAR_LANCAMENTO";
	private static final String TEMPLATE = "mail/aviso-lancamentos-vencidos";
	private static final String ASSUNTO_EMAIL = "Lançamentos vencidos";
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private EconomicApiProperty property;
	
	
	@Scheduled(cron = "0 0 6 * * *")
	public void notificar() {
		List<Lancamento> lancamentos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
	
		if(!lancamentos.isEmpty()) {
			List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao(PERMISSAO);
			this.avisarSobreLancamentosVencidos(lancamentos, destinatarios);
		}
	}
	
	private void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, 
			List<Usuario> destinatarios) {
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		List<String> emails = destinatarios.stream()
				.map(Usuario::getEmail)
				.collect(Collectors.toList());
		
		this.mailer.sendMail(property.getMail().getUsername(), emails, 
				ASSUNTO_EMAIL, 
				TEMPLATE, 
				variaveis);
	}
	
}
