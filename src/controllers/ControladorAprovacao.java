package controllers;

import models.Candidato;
import models.StatusCandidato;
import models.Rejeicao;
import service.*;
import java.util.List;
import java.util.Optional;

/**
 * Controlador principal do caso de uso "Aprovar Afiliação"
 * Implementa o fluxo do diagrama de sequência
 */
public class ControladorAprovacao {
    private final CandidatoService candidatoService;
    private final AfiliacaoService afiliacaoService;
    private final AceiteService aceiteService;
    private final EmailService emailService;
    private final RejeicaoService rejeicaoService;

    public ControladorAprovacao(CandidatoService candidatoService,
                                AfiliacaoService afiliacaoService,
                                AceiteService aceiteService,
                                EmailService emailService,
                                RejeicaoService rejeicaoService) {
        this.candidatoService = candidatoService;
        this.afiliacaoService = afiliacaoService;
        this.aceiteService = aceiteService;
        this.emailService = emailService;
        this.rejeicaoService = rejeicaoService;
    }

    /**
     * Retorna lista de candidatos pendentes de aprovação
     */
    public List<Candidato> listarPendentes() {
        System.out.println("[ControladorAprovacao] Listando candidatos pendentes...");
        return candidatoService.listarPendentes();
    }

    /**
     * Executa o fluxo completo de aprovação de candidato conforme diagrama de sequência:
     * 1. obterCandidato()
     * 2. criarAfiliacao()
     * 3. registrarAceite()
     * 4. atualizarStatus()
     * 5. removerPendencia()
     * 6. enviarAprovacao()
     */
    public boolean aprovarCandidato(String candidatoId, String representanteId) {
        try {
            System.out.println("\n[ControladorAprovacao] Iniciando processo de aprovação...");

            // 1. Obter candidato
            Optional<Candidato> candidatoOpt = candidatoService.obterCandidato(candidatoId);
            if (!candidatoOpt.isPresent()) {
                System.err.println("[ControladorAprovacao] Candidato não encontrado!");
                return false;
            }

            Candidato candidato = candidatoOpt.get();
            System.out.println("[ControladorAprovacao] Candidato obtido: " + candidato.getNome());

            // Verifica se ainda está pendente
            if (!candidatoService.podeAprovar(candidato)) {
                System.err.println("[ControladorAprovacao] Candidato já processado. Status: " + candidato.getStatus());
                return false;
            }
            if (representanteId == null || representanteId.trim().isEmpty()) {
                System.err.println("[ControladorAprovacao] Representante inválido.");
                return false;
            }
            // Início de sequência (sem transação explícita SQLite: simplificado)
            afiliacaoService.criarAfiliacao(candidatoId);
            aceiteService.registrarAceite(representanteId, candidatoId);
            candidatoService.atualizarStatus(candidato, StatusCandidato.APROVADO);
            candidatoService.removerPendencia(candidato);
            emailService.enviarAprovacao(candidato);

            System.out.println("[ControladorAprovacao] Aprovação concluída com sucesso!\n");
            return true;

        } catch (Exception e) {
            System.err.println("[ControladorAprovacao] Erro ao aprovar candidato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Aprovação simplificada utilizada pela camada de apresentação que não coleta representante.
     * Encaminha para o método completo usando um representante padrão.
     */
    public boolean aprovarAfiliacao(String candidatoId) {
        final String representantePadrao = "REPRESENTANTE_PADRAO";
        return aprovarCandidato(candidatoId, representantePadrao);
    }

    /**
     * Fluxo de rejeição de afiliação:
     * 1. obterCandidato
     * 2. atualizarStatus -> REJEITADO
     * 3. removerPendencia
     * 4. enviarRejeicao (e-mail informativo)
     */
    public boolean rejeitarAfiliacao(String candidatoId, String motivo) {
        try {
            System.out.println("\n[ControladorAprovacao] Iniciando processo de rejeição...");
            Optional<Candidato> candidatoOpt = candidatoService.obterCandidato(candidatoId);
            if (!candidatoOpt.isPresent()) {
                System.err.println("[ControladorAprovacao] Candidato não encontrado para rejeição!");
                return false;
            }
            Candidato candidato = candidatoOpt.get();
            System.out.println("[ControladorAprovacao] Candidato obtido: " + candidato.getNome());
            if (!candidatoService.podeAprovar(candidato)) {
                System.err.println("[ControladorAprovacao] Candidato não está mais pendente. Status: " + candidato.getStatus());
                return false;
            }
            // Representante padrão para rejeição se não houver
            String representanteId = "REPRESENTANTE_PADRAO";
            Rejeicao r = rejeicaoService.registrarRejeicao(candidato, representanteId, motivo);
            emailService.enviarRejeicao(candidato, motivo);
            System.out.println("[ControladorAprovacao] Rejeição registrada: " + r.getId());
            System.out.println("[ControladorAprovacao] Rejeição concluída com sucesso!\n");
            return true;
        } catch (Exception e) {
            System.err.println("[ControladorAprovacao] Erro ao rejeitar candidato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
