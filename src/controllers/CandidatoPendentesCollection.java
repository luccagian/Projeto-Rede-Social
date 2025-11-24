package controllers;

import models.Candidato;
import java.util.ArrayList;
import java.util.List;

/**
 * Coleção para gerenciar candidatos pendentes em memória
 */
public class CandidatoPendentesCollection {
    private List<Candidato> candidatosPendentes;

    public CandidatoPendentesCollection() {
        this.candidatosPendentes = new ArrayList<>();
    }

    public void adicionar(Candidato candidato) {
        candidatosPendentes.add(candidato);
    }

    public void remover(Candidato candidato) {
        candidatosPendentes.remove(candidato);
    }

    public List<Candidato> listar() {
        return new ArrayList<>(candidatosPendentes);
    }

    public int tamanho() {
        return candidatosPendentes.size();
    }
}
