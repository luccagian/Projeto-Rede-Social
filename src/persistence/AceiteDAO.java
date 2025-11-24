package persistence;

import models.Aceite;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * DAO para operações de persistência da entidade Aceite
 */
public class AceiteDAO {
    private final Connection connection;

    public AceiteDAO() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    public void salvar(Aceite aceite) {
        String sql = "INSERT INTO aceites (id, representante_id, candidato_id, data_aceite) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, aceite.getId());
            pstmt.setString(2, aceite.getRepresentanteId());
            pstmt.setString(3, aceite.getCandidatoId());
            pstmt.setString(4, aceite.getDataAceite().toString());
            
            pstmt.executeUpdate();
            System.out.println("[AceiteDAO] Aceite salvo: " + aceite.getId());
        } catch (SQLException e) {
            System.err.println("[AceiteDAO] Erro ao salvar aceite: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<Aceite> buscarPorId(String id) {
        String sql = "SELECT * FROM aceites WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Aceite(
                        rs.getString("id"),
                        rs.getString("representante_id"),
                        rs.getString("candidato_id"),
                        LocalDateTime.parse(rs.getString("data_aceite"))
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("[AceiteDAO] Erro ao buscar aceite: " + e.getMessage());
        }
        
        return Optional.empty();
    }
}
