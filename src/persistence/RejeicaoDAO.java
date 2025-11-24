package persistence;

import models.Rejeicao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO para persistência de rejeições.
 */
public class RejeicaoDAO {
    private final Connection connection;

    public RejeicaoDAO() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    public void salvar(Rejeicao rejeicao) {
        String sql = "INSERT INTO rejeicoes (id, candidato_id, representante_id, motivo, data_rejeicao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, rejeicao.getId());
            pstmt.setString(2, rejeicao.getCandidatoId());
            pstmt.setString(3, rejeicao.getRepresentanteId());
            pstmt.setString(4, rejeicao.getMotivo());
            pstmt.setString(5, rejeicao.getDataRejeicao().toString());
            pstmt.executeUpdate();
            System.out.println("[RejeicaoDAO] Rejeição salva: " + rejeicao.getId());
        } catch (SQLException e) {
            System.err.println("[RejeicaoDAO] Erro ao salvar rejeição: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
