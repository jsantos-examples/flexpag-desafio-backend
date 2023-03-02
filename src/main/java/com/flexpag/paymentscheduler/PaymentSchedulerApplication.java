package com.flexpag.paymentscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PaymentSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSchedulerApplication.class, args);
	}

	@GetMapping("/")
	public String tutorialPage(){
		return "<h1>Sistema de agendamento de pagamentos</h1>" +
				"<p>Para utilizar o sistema, você deverá fazer requisições a rota localhost:8080/TODO</p>" + //atualizar para nova rota
				"<p>As opções de uso do sistema de agendamento são:</p>" +
				"<ul>" +
				"<li>Adicionar agendamento</li>" +
				"<li>Realizar pagamento</li>" +
				"<li>Editar agendamento</li>" +
				"<li>Excluir agendamento</li>" +
				"</ul>";
	}

}
