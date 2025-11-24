package service;

import models.Afiliacao;
import persistence.AfiliacaoDAO;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Serviço para operações de negócio relacionadas a Afiliações
 */
public class AfiliacaoService {
    private final AfiliacaoDAO afiliacaoDAO;

    public AfiliacaoService(AfiliacaoDAO afiliacaoDAO) {
        this.afiliacaoDAO = afiliacaoDAO;
    }

    public Afiliacao criarAfiliacao(String candidatoId) {
        Afiliacao afiliacao = new Afiliacao(
            UUID.randomUUID().toString(),
            candidatoId,
            LocalDateTime.now()
        );
        
        afiliacaoDAO.salvar(afiliacao);
        System.out.println("[AfiliacaoService] Afiliação criada para candidato: " + candidatoId);
        
        return afiliacao;
    }
}
