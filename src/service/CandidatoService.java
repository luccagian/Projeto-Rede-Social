package service;

import models.Candidato;
import models.StatusCandidato;
import persistence.CandidatoDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço para operações de negócio relacionadas a Candidatos
 */
public class CandidatoService {
    private final CandidatoDAO candidatoDAO;

    public CandidatoService(CandidatoDAO candidatoDAO) {
        this.candidatoDAO = candidatoDAO;
    }

    public Candidato cadastrar(String nome, String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        candidatoDAO.buscarPorEmail(email).ifPresent(c -> {
            throw new IllegalArgumentException("Email já cadastrado: " + email);
        });
        Candidato candidato = new Candidato(
                UUID.randomUUID().toString(),
                nome,
                email,
                StatusCandidato.PENDENTE,
                LocalDateTime.now(),
                true
        );
        candidatoDAO.salvar(candidato);
        System.out.println("[CandidatoService] Candidato cadastrado: " + nome);
        return candidato;
    }

    public void salvarCandidato(Candidato candidato) {
        candidatoDAO.salvar(candidato);
    }

    public Optional<Candidato> obterCandidato(String id) {
        return candidatoDAO.buscarPorId(id);
    }

    public List<Candidato> listarPendentes() {
        return candidatoDAO.listarPendentes();
    }

    public void atualizarStatus(Candidato candidato, StatusCandidato novoStatus) {
        candidato.setStatus(novoStatus);
        candidatoDAO.atualizar(candidato);
        System.out.println("[CandidatoService] Status atualizado para " + novoStatus);
    }

    public void removerPendencia(Candidato candidato) {
        candidato.setPendente(false);
        candidatoDAO.atualizar(candidato);
        System.out.println("[CandidatoService] Pendência removida do candidato " + candidato.getNome());
    }

    public boolean podeAprovar(Candidato candidato) {
        return candidato.getStatus() == StatusCandidato.PENDENTE;
    }
}
