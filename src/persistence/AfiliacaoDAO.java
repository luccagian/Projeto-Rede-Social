package persistence;

import models.Afiliacao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * DAO para operações de persistência da entidade Afiliação
 */
public class AfiliacaoDAO {
    private final Connection connection;

    public AfiliacaoDAO() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    public void salvar(Afiliacao afiliacao) {
        String sql = "INSERT INTO afiliacoes (id, candidato_id, data_criacao) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, afiliacao.getId());
            pstmt.setString(2, afiliacao.getCandidatoId());
            pstmt.setString(3, afiliacao.getDataCriacao().toString());
            
            pstmt.executeUpdate();
            System.out.println("[AfiliacaoDAO] Afiliação salva: " + afiliacao.getId());
        } catch (SQLException e) {
            System.err.println("[AfiliacaoDAO] Erro ao salvar afiliação: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<Afiliacao> buscarPorId(String id) {
        String sql = "SELECT * FROM afiliacoes WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Afiliacao(
                        rs.getString("id"),
                        rs.getString("candidato_id"),
                        LocalDateTime.parse(rs.getString("data_criacao"))
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("[AfiliacaoDAO] Erro ao buscar afiliação: " + e.getMessage());
        }
        
        return Optional.empty();
    }
}
