package views;

import controllers.ControladorAprovacao;
import models.Candidato;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaAprovacaoGUI extends JFrame {
    private ControladorAprovacao controlador;
    private JPanel painelPrincipal;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private List<Candidato> candidatosPendentes;
    private int candidatoAtualIndex = 0;

    public TelaAprovacaoGUI(ControladorAprovacao controlador) {
        this.controlador = controlador;
        inicializarComponentes();
        carregarCandidatos();
    }

    private void inicializarComponentes() {
        setTitle("Tela de aprovação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.WHITE);

        // Header
        criarHeader();

        // Content
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        painelPrincipal.add(contentPanel, BorderLayout.CENTER);

        // Bottom Navigation
        criarNavegacaoInferior();

        add(painelPrincipal);
    }

    private void criarHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 64, 153));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        headerPanel.setPreferredSize(new Dimension(420, 100));

        // Logo com círculo
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(new Color(25, 64, 153));
        
        JLabel logoCirculo = new JLabel("●");
        logoCirculo.setForeground(Color.WHITE);
        logoCirculo.setFont(new Font("Arial", Font.BOLD, 40));
        logoCirculo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoTexto = new JLabel("REDE");
        logoTexto.setForeground(Color.WHITE);
        logoTexto.setFont(new Font("Arial", Font.BOLD, 12));
        logoTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoTexto2 = new JLabel("+MAIS");
        logoTexto2.setForeground(Color.WHITE);
        logoTexto2.setFont(new Font("Arial", Font.BOLD, 12));
        logoTexto2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel logoTexto3 = new JLabel("SOCIAL");
        logoTexto3.setForeground(Color.WHITE);
        logoTexto3.setFont(new Font("Arial", Font.BOLD, 12));
        logoTexto3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(logoCirculo);
        logoPanel.add(logoTexto);
        logoPanel.add(logoTexto2);
        logoPanel.add(logoTexto3);

        // Ícone de perfil circular
        JLabel perfilIcon = new JLabel("👤");
        perfilIcon.setForeground(Color.WHITE);
        perfilIcon.setFont(new Font("Arial", Font.PLAIN, 35));
        perfilIcon.setOpaque(true);
        perfilIcon.setBackground(new Color(25, 64, 153));
        perfilIcon.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 3),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(perfilIcon, BorderLayout.EAST);

        painelPrincipal.add(headerPanel, BorderLayout.NORTH);
    }

    private void criarNavegacaoInferior() {
        JPanel navPanel = new JPanel(new GridLayout(1, 5));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(200, 200, 200)));
        navPanel.setPreferredSize(new Dimension(420, 80));

        String[] icones = {"🏠", "👥", "➕", "📅", "🔔"};
        for (String icone : icones) {
            JButton btn = new JButton(icone);
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            navPanel.add(btn);
        }

        painelPrincipal.add(navPanel, BorderLayout.SOUTH);
    }

    private void carregarCandidatos() {
        candidatosPendentes = controlador.listarPendentes();
        if (!candidatosPendentes.isEmpty()) {
            exibirCandidato(candidatoAtualIndex);
        } else {
            exibirMensagemSemCandidatos();
        }
    }

    private void exibirCandidato(int index) {
        contentPanel.removeAll();
        
        if (index >= 0 && index < candidatosPendentes.size()) {
            Candidato candidato = candidatosPendentes.get(index);
            
            // Título
            JLabel titulo = new JLabel("Candidato a Aprovação:");
            titulo.setFont(new Font("Serif", Font.BOLD, 20));
            titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(titulo);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

            // Informações detalhadas do candidato
            adicionarInfoDetalhada(candidato);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            // Botões de ação
            JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            botoesPanel.setBackground(Color.WHITE);
            botoesPanel.setMaximumSize(new Dimension(380, 55));

            JButton btnRejeitar = criarBotao("Rejeitar", Color.WHITE, Color.BLACK);
            btnRejeitar.addActionListener(e -> abrirTelaRejeicao(candidato));

            JButton btnAprovar = criarBotao("Aprovar", new Color(65, 105, 225), Color.WHITE);
            btnAprovar.addActionListener(e -> abrirTelaRecomendacao(candidato));

            botoesPanel.add(btnRejeitar);
            botoesPanel.add(btnAprovar);
            contentPanel.add(botoesPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void adicionarInfoDetalhada(Candidato candidato) {
        // Painel com borda para informações
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        infoPanel.setMaximumSize(new Dimension(370, 380));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Candidato
        adicionarCampo(infoPanel, "Candidato:", candidato.getNome());
        infoPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Email
        adicionarCampo(infoPanel, "Email:", candidato.getEmail());
        infoPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Perfil
        adicionarCampo(infoPanel, "Perfil:", "Animado e feliz");
        infoPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Habilidades
        adicionarCampo(infoPanel, "Habilidades:", "Excel, Java e semi-profissional em escalada");
        infoPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Interesses
        adicionarCampo(infoPanel, "Interesses:", "Futebol e história");
        infoPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // ID discreto no final
        JLabel idLabel = new JLabel("ID: " + candidato.getId());
        idLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        idLabel.setForeground(new Color(150, 150, 150));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(idLabel);

        contentPanel.add(infoPanel);
    }

    private void adicionarCampo(JPanel panel, String label, String valor) {
        // Label
        JLabel labelTexto = new JLabel(label);
        labelTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        labelTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(labelTexto);

        panel.add(Box.createRigidArea(new Dimension(0, 4)));

        // Valor
        JLabel valorLabel = new JLabel(valor);
        valorLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        valorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        valorLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        panel.add(valorLabel);
    }

    private JButton criarBotao(String texto, Color bgColor, Color fgColor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 15));
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setPreferredSize(new Dimension(150, 50));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void abrirTelaRejeicao(Candidato candidato) {
        TelaRejeicaoGUI telaRejeicao = new TelaRejeicaoGUI(this, controlador, candidato);
        telaRejeicao.setVisible(true);
        this.setVisible(false);
    }

    private void abrirTelaRecomendacao(Candidato candidato) {
        TelaRecomendacaoGUI telaRecomendacao = new TelaRecomendacaoGUI(this, controlador, candidato);
        telaRecomendacao.setVisible(true);
        this.setVisible(false);
    }

    private void exibirMensagemSemCandidatos() {
        contentPanel.removeAll();
        
        JLabel mensagem = new JLabel("Não há candidatos pendentes de aprovação");
        mensagem.setFont(new Font("Arial", Font.PLAIN, 16));
        mensagem.setForeground(Color.GRAY);
        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(mensagem);
        contentPanel.add(Box.createVerticalGlue());
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void atualizarLista() {
        carregarCandidatos();
    }
}
