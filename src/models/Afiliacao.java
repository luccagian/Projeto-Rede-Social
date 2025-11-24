package models;

import java.time.LocalDateTime;

/**
 * Entidade Afiliação - representa a afiliação de um candidato aprovado
 */
public class Afiliacao {
    private String id;
    private String candidatoId;
    private LocalDateTime dataCriacao;

    public Afiliacao(String id, String candidatoId, LocalDateTime dataCriacao) {
        this.id = id;
        this.candidatoId = candidatoId;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public String getId() { return id; }
    public String getCandidatoId() { return candidatoId; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setCandidatoId(String candidatoId) { this.candidatoId = candidatoId; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public String toString() {
        return String.format("Afiliacao[id=%s, candidatoId=%s, dataCriacao=%s]", 
                           id, candidatoId, dataCriacao);
    }
}
