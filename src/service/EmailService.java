package service;

import models.Candidato;

/**
 * Serviço para envio de e-mails de notificação
 * Implementação simulada para fins do diagrama de sequência
 */
public class EmailService {

    public void enviarAprovacao(Candidato candidato) {
        // Simulação de envio de e-mail
        System.out.println("=====================================");
        System.out.println("[EmailService] E-mail de aprovação enviado");
        System.out.println("Para: " + candidato.getEmail());
        System.out.println("Nome: " + candidato.getNome());
        System.out.println("Assunto: Sua afiliação foi aprovada!");
        System.out.println("Mensagem: Parabéns! Sua solicitação de afiliação foi aprovada.");
        System.out.println("=====================================");
    }

    public void enviarRejeicao(Candidato candidato, String motivo) {
        System.out.println("=====================================");
        System.out.println("[EmailService] E-mail de rejeição enviado");
        System.out.println("Para: " + candidato.getEmail());
        System.out.println("Nome: " + candidato.getNome());
        System.out.println("Assunto: Sua afiliação foi rejeitada");
        System.out.println("Motivo: " + motivo);
        System.out.println("Mensagem: Agradecemos seu interesse. Você pode se candidatar novamente futuramente.");
        System.out.println("=====================================");
    }
}
