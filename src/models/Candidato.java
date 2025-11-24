package models;

import java.time.LocalDateTime;

/**
 * Entidade Candidato - representa um usuário candidato à afiliação na rede social
 */
public class Candidato {
    private String id;
    private String nome;
    private String email;
    private StatusCandidato status; // Usa enum em vez de String
    private LocalDateTime dataCadastro;
    private boolean pendente;

    public Candidato(String id, String nome, String email, StatusCandidato status,
                     LocalDateTime dataCadastro, boolean pendente) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.status = status;
        this.dataCadastro = dataCadastro;
        this.pendente = pendente;
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public StatusCandidato getStatus() { return status; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public boolean isPendente() { return pendente; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setStatus(StatusCandidato status) { this.status = status; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    public void setPendente(boolean pendente) { this.pendente = pendente; }

    @Override
    public String toString() {
        return String.format("Candidato[id=%s, nome=%s, email=%s, status=%s]",
            id, nome, email, status);
    }
}
