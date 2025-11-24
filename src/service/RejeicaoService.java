package service;

import models.Candidato;
import models.Rejeicao;
import models.StatusCandidato;
import persistence.CandidatoDAO;
import persistence.RejeicaoDAO;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Serviço de regras de negócio para rejeições.
 */
public class RejeicaoService {
    private final RejeicaoDAO rejeicaoDAO;
    private final CandidatoDAO candidatoDAO;

    public RejeicaoService(RejeicaoDAO rejeicaoDAO, CandidatoDAO candidatoDAO) {
        this.rejeicaoDAO = rejeicaoDAO;
        this.candidatoDAO = candidatoDAO;
    }

    public Rejeicao registrarRejeicao(Candidato candidato, String representanteId, String motivo) {
        Rejeicao rejeicao = new Rejeicao(
                UUID.randomUUID().toString(),
                candidato.getId(),
                representanteId,
                motivo,
                LocalDateTime.now()
        );
        // Atualiza status candidato
        candidato.setStatus(StatusCandidato.REJEITADO);
        candidato.setPendente(false);
        candidatoDAO.atualizar(candidato);
        // Persiste rejeição
        rejeicaoDAO.salvar(rejeicao);
        return rejeicao;
    }
}
