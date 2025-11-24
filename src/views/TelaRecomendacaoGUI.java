package views;

import controllers.ControladorAprovacao;
import models.Candidato;

import javax.swing.*;
import java.awt.*;

public class TelaRecomendacaoGUI extends JFrame {
    private TelaAprovacaoGUI telaAnterior;
    private ControladorAprovacao controlador;
    private Candidato candidato;
    private JTextField representanteField;

    public TelaRecomendacaoGUI(TelaAprovacaoGUI telaAnterior, ControladorAprovacao controlador, Candidato candidato) {
        this.telaAnterior = telaAnterior;
        this.controlador = controlador;
        this.candidato = candidato;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Tela de Recomendação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.WHITE);

        criarHeader(painelPrincipal);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        adicionarInfoCandidato(contentPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        adicionarSecaoONGs(contentPanel);
        contentPanel.add(Box.createVerticalGlue());
        adicionarBotoesAcao(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        criarNavegacaoInferior(painelPrincipal);

        add(painelPrincipal);
    }

    private void criarHeader(JPanel painelPrincipal) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 64, 153));
        headerPanel.setPreferredSize(new Dimension(420, 140));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(25, 64, 153));
        topBar.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(new Color(25, 64, 153));
        
        JLabel logoCirculo = new JLabel("●");
        logoCirculo.setForeground(Color.WHITE);
        logoCirculo.setFont(new Font("Arial", Font.BOLD, 32));
        logoCirculo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoTexto = new JLabel("REDE");
        logoTexto.setForeground(Color.WHITE);
        logoTexto.setFont(new Font("Arial", Font.BOLD, 10));
        logoTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoTexto2 = new JLabel("+MAIS");
        logoTexto2.setForeground(Color.WHITE);
        logoTexto2.setFont(new Font("Arial", Font.BOLD, 10));
        logoTexto2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoTexto3 = new JLabel("SOCIAL");
        logoTexto3.setForeground(Color.WHITE);
        logoTexto3.setFont(new Font("Arial", Font.BOLD, 10));
        logoTexto3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(logoCirculo);
        logoPanel.add(logoTexto);
        logoPanel.add(logoTexto2);
        logoPanel.add(logoTexto3);

        JLabel perfilIcon = new JLabel("👤");
        perfilIcon.setForeground(Color.WHITE);
        perfilIcon.setFont(new Font("Arial", Font.PLAIN, 32));
        perfilIcon.setOpaque(true);
        perfilIcon.setBackground(new Color(25, 64, 153));
        perfilIcon.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 3),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        topBar.add(logoPanel, BorderLayout.WEST);
        topBar.add(perfilIcon, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(25, 64, 153));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 20, 25));

        JTextField searchField = new JTextField("  🔍  PROCURE AQUI...");
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setForeground(new Color(150, 150, 150));
        searchField.setPreferredSize(new Dimension(370, 40));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        searchPanel.add(searchField, BorderLayout.CENTER);

        headerPanel.add(topBar, BorderLayout.NORTH);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        painelPrincipal.add(headerPanel, BorderLayout.NORTH);
    }

    private void adicionarInfoCandidato(JPanel contentPanel) {
        JLabel candidatoLabel = new JLabel("Candidato: " + candidato.getNome());
        candidatoLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        candidatoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(candidatoLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel perfilLabel = new JLabel("Perfil: Animado e feliz");
        perfilLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        perfilLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(perfilLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        JLabel habilidadesLabel = new JLabel("Habilidades: Excel, Java e semi-profissional");
        habilidadesLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        habilidadesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(habilidadesLabel);
        
        JLabel habilidadesLabel2 = new JLabel("em escalada");
        habilidadesLabel2.setFont(new Font("Arial", Font.PLAIN, 15));
        habilidadesLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(habilidadesLabel2);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        JLabel interessesLabel = new JLabel("Interesses: Futebol e história");
        interessesLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        interessesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(interessesLabel);
    }

    private void adicionarSecaoONGs(JPanel contentPanel) {
        JLabel ongsLabel = new JLabel("ONGs e Campanhas que combinam");
        ongsLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        ongsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(ongsLabel);
        
        JLabel ongsLabel2 = new JLabel("com este candidato:");
        ongsLabel2.setFont(new Font("Arial", Font.PLAIN, 15));
        ongsLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(ongsLabel2);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel mensagemLabel = new JLabel("Não há nenhuma ONG ou Campanha compatível");
        mensagemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mensagemLabel.setForeground(new Color(100, 100, 100));
        mensagemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(mensagemLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Campo de representante após a seção de ONGs
        JLabel representanteLabel = new JLabel("ID do Representante:");
        representanteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        representanteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(representanteLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        representanteField = new JTextField();
        representanteField.setMaximumSize(new Dimension(360, 40));
        representanteField.setPreferredSize(new Dimension(360, 40));
        representanteField.setFont(new Font("Arial", Font.PLAIN, 14));
        representanteField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        representanteField.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(representanteField);
    }

    private void adicionarBotoesAcao(JPanel contentPanel) {
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        botoesPanel.setBackground(Color.WHITE);
        botoesPanel.setMaximumSize(new Dimension(370, 55));
        botoesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnReprovar = new JButton("Reprovar");
        btnReprovar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnReprovar.setBackground(Color.WHITE);
        btnReprovar.setForeground(Color.BLACK);
        btnReprovar.setPreferredSize(new Dimension(150, 50));
        btnReprovar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        btnReprovar.setFocusPainted(false);
        btnReprovar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReprovar.addActionListener(e -> voltarParaRejeicao());

        JButton btnAprovar = new JButton("Aprovar");
        btnAprovar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnAprovar.setBackground(new Color(65, 105, 225));
        btnAprovar.setForeground(Color.WHITE);
        btnAprovar.setPreferredSize(new Dimension(150, 50));
        btnAprovar.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2, true));
        btnAprovar.setFocusPainted(false);
        btnAprovar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAprovar.addActionListener(e -> aprovarCandidato());

        botoesPanel.add(btnReprovar);
        botoesPanel.add(btnAprovar);
        contentPanel.add(botoesPanel);
    }

    private void criarNavegacaoInferior(JPanel painelPrincipal) {
        JPanel navPanel = new JPanel(new GridLayout(1, 5));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(200, 200, 200)));
        navPanel.setPreferredSize(new Dimension(420, 80));

        String[] icones = {"🏠", "👥", "➕", "📅", "🔔"};
        for (String icone : icones) {
            JButton btn = new JButton(icone);
            btn.setFont(new Font("Arial", Font.PLAIN, 28));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            navPanel.add(btn);
        }

        painelPrincipal.add(navPanel, BorderLayout.SOUTH);
    }

    private void aprovarCandidato() {
        String representanteId = representanteField.getText().trim();
        if (representanteId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Informe o ID do representante antes de aprovar.",
                "Campo obrigatório",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean sucesso = controlador.aprovarCandidato(candidato.getId(), representanteId);

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                "Candidato aprovado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            voltarTelaInicial();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao aprovar candidato.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarParaRejeicao() {
        TelaRejeicaoGUI telaRejeicao = new TelaRejeicaoGUI(telaAnterior, controlador, candidato);
        telaRejeicao.setVisible(true);
        this.dispose();
    }

    private void voltarTelaInicial() {
        telaAnterior.atualizarLista();
        telaAnterior.setVisible(true);
        this.dispose();
    }
}
