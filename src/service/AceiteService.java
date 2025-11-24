package service;

import models.Aceite;
import persistence.AceiteDAO;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Serviço para operações de negócio relacionadas a Aceites
 */
public class AceiteService {
    private final AceiteDAO aceiteDAO;

    public AceiteService(AceiteDAO aceiteDAO) {
        this.aceiteDAO = aceiteDAO;
    }

    public Aceite registrarAceite(String representanteId, String candidatoId) {
        Aceite aceite = new Aceite(
            UUID.randomUUID().toString(),
            representanteId,
            candidatoId,
            LocalDateTime.now()
        );
        
        aceiteDAO.salvar(aceite);
        System.out.println("[AceiteService] Aceite registrado para candidato: " + candidatoId);
        
        return aceite;
    }
}
