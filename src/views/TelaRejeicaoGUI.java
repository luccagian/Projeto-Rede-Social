package views;

import controllers.ControladorAprovacao;
import models.Candidato;

import javax.swing.*;
import java.awt.*;

public class TelaRejeicaoGUI extends JFrame {
    private TelaAprovacaoGUI telaAnterior;
    private ControladorAprovacao controlador;
    private Candidato candidato;
    private JTextField motivoField;

    public TelaRejeicaoGUI(TelaAprovacaoGUI telaAnterior, ControladorAprovacao controlador, Candidato candidato) {
        this.telaAnterior = telaAnterior;
        this.controlador = controlador;
        this.candidato = candidato;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Tela de aprovação");
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
        contentPanel.setBorder(BorderFactory.createEmptyBorder(80, 30, 40, 30));

        JLabel tituloLabel = new JLabel("Motivo da rejeição:");
        tituloLabel.setFont(new Font("Serif", Font.PLAIN, 28));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tituloLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 80)));

        motivoField = new JTextField();
        motivoField.setFont(new Font("Arial", Font.PLAIN, 16));
        motivoField.setMaximumSize(new Dimension(360, 50));
        motivoField.setPreferredSize(new Dimension(360, 50));
        motivoField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        motivoField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(motivoField);
        contentPanel.add(Box.createVerticalGlue());

        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnContinuar.setBackground(Color.WHITE);
        btnContinuar.setForeground(Color.BLACK);
        btnContinuar.setPreferredSize(new Dimension(180, 50));
        btnContinuar.setMaximumSize(new Dimension(180, 50));
        btnContinuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContinuar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        btnContinuar.setFocusPainted(false);
        btnContinuar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnContinuar.addActionListener(e -> rejeitarCandidato());

        contentPanel.add(btnContinuar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        painelPrincipal.add(contentPanel, BorderLayout.CENTER);
        criarNavegacaoInferior(painelPrincipal);

        add(painelPrincipal);
    }

    private void criarHeader(JPanel painelPrincipal) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 64, 153));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        headerPanel.setPreferredSize(new Dimension(420, 100));

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

    private void rejeitarCandidato() {
        String motivo = motivoField.getText().trim();
        
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, informe o motivo da rejeição.",
                "Campo Obrigatório",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean sucesso = controlador.rejeitarAfiliacao(candidato.getId(), motivo);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                "Candidato rejeitado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            voltarTelaAnterior();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao rejeitar candidato.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltarTelaAnterior() {
        telaAnterior.atualizarLista();
        telaAnterior.setVisible(true);
        this.dispose();
    }
}
