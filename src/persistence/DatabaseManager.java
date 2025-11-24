package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável por gerenciar a conexão e inicialização do banco de dados SQLite
 * Implementa o padrão Singleton para garantir uma única instância da conexão
 */
public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:rede_social.db";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            // Carrega o driver JDBC do SQLite
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
            inicializarTabelas();
            System.out.println("[DatabaseManager] Conectado ao banco de dados SQLite");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[DatabaseManager] Erro ao conectar ao banco: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL);
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Erro ao obter conexão: " + e.getMessage());
        }
        return connection;
    }

    private void inicializarTabelas() {
        try (Statement stmt = connection.createStatement()) {
            // Tabela de Candidatos
            String createCandidatos = "CREATE TABLE IF NOT EXISTS candidatos (" +
                    "id TEXT PRIMARY KEY," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "status TEXT NOT NULL CHECK(status IN ('PENDENTE', 'APROVADO', 'REJEITADO'))," +
                    "data_cadastro TEXT NOT NULL," +
                    "pendente INTEGER NOT NULL CHECK(pendente IN (0, 1))" +
                ")";

            String createAfiliacoes = "CREATE TABLE IF NOT EXISTS afiliacoes (" +
                    "id TEXT PRIMARY KEY," +
                    "candidato_id TEXT NOT NULL," +
                    "data_criacao TEXT NOT NULL," +
                    "FOREIGN KEY (candidato_id) REFERENCES candidatos(id)" +
                ")";

            String createAceites = "CREATE TABLE IF NOT EXISTS aceites (" +
                    "id TEXT PRIMARY KEY," +
                    "representante_id TEXT NOT NULL," +
                    "candidato_id TEXT NOT NULL," +
                    "data_aceite TEXT NOT NULL," +
                    "FOREIGN KEY (candidato_id) REFERENCES candidatos(id)" +
                ")";

            String createRejeicoes = "CREATE TABLE IF NOT EXISTS rejeicoes (" +
                    "id TEXT PRIMARY KEY," +
                    "candidato_id TEXT NOT NULL," +
                    "representante_id TEXT NOT NULL," +
                    "motivo TEXT NOT NULL," +
                    "data_rejeicao TEXT NOT NULL," +
                    "FOREIGN KEY (candidato_id) REFERENCES candidatos(id)" +
                ")";

            stmt.execute(createCandidatos);
            stmt.execute(createAfiliacoes);
            stmt.execute(createAceites);
            stmt.execute(createRejeicoes);

            System.out.println("[DatabaseManager] Tabelas criadas/verificadas com sucesso");
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Erro ao criar tabelas: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DatabaseManager] Conexão com o banco fechada");
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseManager] Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
