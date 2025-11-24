import views.TelaAprovacaoAfiliacao;
import controllers.ControladorAprovacao;
import service.CandidatoService;
import service.AfiliacaoService;
import service.AceiteService;
import service.EmailService;
import service.RejeicaoService;
import persistence.CandidatoDAO;
import persistence.AfiliacaoDAO;
import persistence.AceiteDAO;
import persistence.RejeicaoDAO;
import persistence.DatabaseManager;

/**
 * Classe principal da aplicação - Sistema de Aprovação de Afiliação (modo console).
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("===========================================");
            System.out.println("  INICIANDO SISTEMA DE REDE SOCIAL");
            System.out.println("  Caso de Uso: Aprovar Afiliação");
            System.out.println("===========================================\n");

            System.out.println("[Main] Inicializando banco de dados...");
            DatabaseManager.getInstance();

            CandidatoDAO candidatoDAO = new CandidatoDAO();
            AfiliacaoDAO afiliacaoDAO = new AfiliacaoDAO();
            AceiteDAO aceiteDAO = new AceiteDAO();
            RejeicaoDAO rejeicaoDAO = new RejeicaoDAO();

            CandidatoService candidatoService = new CandidatoService(candidatoDAO);
            AfiliacaoService afiliacaoService = new AfiliacaoService(afiliacaoDAO);
            AceiteService aceiteService = new AceiteService(aceiteDAO);
            EmailService emailService = new EmailService();

            System.out.println("\n[Main] Populando dados de exemplo...");
            popularDadosExemplo(candidatoService);

            RejeicaoService rejeicaoService = new RejeicaoService(rejeicaoDAO, candidatoDAO);

            ControladorAprovacao controlador = new ControladorAprovacao(
                    candidatoService,
                    afiliacaoService,
                    aceiteService,
                    emailService,
                    rejeicaoService
            );

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    DatabaseManager.getInstance().fecharConexao();
                    System.out.println("[ShutdownHook] Conexão fechada com sucesso.");
                } catch (Exception ex) {
                    System.err.println("[ShutdownHook] Falha ao fechar conexão: " + ex.getMessage());
                }
            }));

            TelaAprovacaoAfiliacao tela = new TelaAprovacaoAfiliacao(controlador);
            System.out.println("\n[Main] Sistema iniciado (modo console)!\n");
            tela.exibirMenu();

            System.out.println("\n[Main] Encerrando conexão com o banco...");
            DatabaseManager.getInstance().fecharConexao();
            System.out.println("[Main] Sistema encerrado.");

        } catch (Exception e) {
            System.err.println("[Main] Erro fatal ao iniciar sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void popularDadosExemplo(CandidatoService candidatoService) {
        try {
            candidatoService.cadastrar("Ana Silva", "ana.silva@exemplo.com");
            candidatoService.cadastrar("Bruno Santos", "bruno.santos@exemplo.com");
            candidatoService.cadastrar("Carla Oliveira", "carla.oliveira@exemplo.com");
            candidatoService.cadastrar("Daniel Costa", "daniel.costa@exemplo.com");
            System.out.println("[Main] 4 candidatos de exemplo cadastrados.");
        } catch (Exception e) {
            System.err.println("[Main] Erro ao popular dados de exemplo: " + e.getMessage());
        }
    }
}
