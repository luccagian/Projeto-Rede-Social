package models;

import java.time.LocalDateTime;

/**
 * Entidade que registra uma rejeição de afiliação para audit trail.
 */
public class Rejeicao {
    private String id;
    private String candidatoId;
    private String representanteId;
    private String motivo;
    private LocalDateTime dataRejeicao;

    public Rejeicao(String id, String candidatoId, String representanteId, String motivo, LocalDateTime dataRejeicao) {
        this.id = id;
        this.candidatoId = candidatoId;
        this.representanteId = representanteId;
        this.motivo = motivo;
        this.dataRejeicao = dataRejeicao;
    }

    public String getId() { return id; }
    public String getCandidatoId() { return candidatoId; }
    public String getRepresentanteId() { return representanteId; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getDataRejeicao() { return dataRejeicao; }
}
