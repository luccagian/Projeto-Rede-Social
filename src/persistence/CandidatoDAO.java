package persistence;

import models.Candidato;
import models.StatusCandidato;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) para operações de persistência da entidade Candidato
 */
public class CandidatoDAO {
    private final Connection connection;

    public CandidatoDAO() {
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    public void salvar(Candidato candidato) {
        String sql = "INSERT INTO candidatos (id, nome, email, status, data_cadastro, pendente) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, candidato.getId());
            pstmt.setString(2, candidato.getNome());
            pstmt.setString(3, candidato.getEmail());
            pstmt.setString(4, candidato.getStatus().name());
            pstmt.setString(5, candidato.getDataCadastro().toString());
            pstmt.setInt(6, candidato.isPendente() ? 1 : 0);
            
            pstmt.executeUpdate();
            System.out.println("[CandidatoDAO] Candidato salvo: " + candidato.getId());
        } catch (SQLException e) {
            System.err.println("[CandidatoDAO] Erro ao salvar candidato: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void atualizar(Candidato candidato) {
        String sql = "UPDATE candidatos SET nome = ?, email = ?, status = ?, pendente = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, candidato.getNome());
            pstmt.setString(2, candidato.getEmail());
            pstmt.setString(3, candidato.getStatus().name());
            pstmt.setInt(4, candidato.isPendente() ? 1 : 0);
            pstmt.setString(5, candidato.getId());
            
            pstmt.executeUpdate();
            System.out.println("[CandidatoDAO] Candidato atualizado: " + candidato.getId());
        } catch (SQLException e) {
            System.err.println("[CandidatoDAO] Erro ao atualizar candidato: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Optional<Candidato> buscarPorId(String id) {
        String sql = "SELECT * FROM candidatos WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCandidato(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[CandidatoDAO] Erro ao buscar candidato: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    public List<Candidato> listarPendentes() {
        List<Candidato> pendentes = new ArrayList<>();
        String sql = "SELECT * FROM candidatos WHERE pendente = 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pendentes.add(mapearCandidato(rs));
            }
            
            System.out.println("[CandidatoDAO] Encontrados " + pendentes.size() + " candidatos pendentes");
        } catch (SQLException e) {
            System.err.println("[CandidatoDAO] Erro ao listar pendentes: " + e.getMessage());
        }
        
        return pendentes;
    }

    private Candidato mapearCandidato(ResultSet rs) throws SQLException {
        return new Candidato(
                rs.getString("id"),
                rs.getString("nome"),
                rs.getString("email"),
                StatusCandidato.valueOf(rs.getString("status")),
                LocalDateTime.parse(rs.getString("data_cadastro")),
                rs.getInt("pendente") == 1
        );
    }

    public Optional<Candidato> buscarPorEmail(String email) {
        String sql = "SELECT * FROM candidatos WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCandidato(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[CandidatoDAO] Erro ao buscar por email: " + e.getMessage());
        }
        return Optional.empty();
    }
}
