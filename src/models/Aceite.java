package models;

import java.time.LocalDateTime;

/**
 * Entidade Aceite - representa o aceite de um representante para um candidato
 */
public class Aceite {
    private String id;
    private String representanteId;
    private String candidatoId;
    private LocalDateTime dataAceite;

    public Aceite(String id, String representanteId, String candidatoId, LocalDateTime dataAceite) {
        this.id = id;
        this.representanteId = representanteId;
        this.candidatoId = candidatoId;
        this.dataAceite = dataAceite;
    }

    // Getters
    public String getId() { return id; }
    public String getRepresentanteId() { return representanteId; }
    public String getCandidatoId() { return candidatoId; }
    public LocalDateTime getDataAceite() { return dataAceite; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setRepresentanteId(String representanteId) { this.representanteId = representanteId; }
    public void setCandidatoId(String candidatoId) { this.candidatoId = candidatoId; }
    public void setDataAceite(LocalDateTime dataAceite) { this.dataAceite = dataAceite; }

    @Override
    public String toString() {
        return String.format("Aceite[id=%s, representanteId=%s, candidatoId=%s, dataAceite=%s]", 
                           id, representanteId, candidatoId, dataAceite);
    }
}
