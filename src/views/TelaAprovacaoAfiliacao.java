package views;

import controllers.ControladorAprovacao;
import models.Candidato;
import java.util.List;
import java.util.Scanner;

/**
 * Camada de apresentação - Interface com o usuário para aprovação de afiliação
 */
public class TelaAprovacaoAfiliacao {
    private final ControladorAprovacao controlador;
    private final Scanner scanner;

    public TelaAprovacaoAfiliacao(ControladorAprovacao controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE APROVAÇÃO DE AFILIAÇÃO");
            System.out.println("========================================");
            System.out.println("1. Listar candidatos pendentes");
            System.out.println("2. Aprovar candidato");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        listarPendentes();
                        break;
                    case 2:
                        aprovarCandidato();
                        break;
                    case 3:
                        continuar = false;
                        System.out.println("Encerrando sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }

        scanner.close();
    }

    private void listarPendentes() {
        System.out.println("\n--- CANDIDATOS PENDENTES ---");
        List<Candidato> pendentes = controlador.listarPendentes();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhum candidato pendente no momento.");
        } else {
            for (int i = 0; i < pendentes.size(); i++) {
                Candidato c = pendentes.get(i);
                System.out.printf("%d. %s - %s (Status: %s)\n", 
                    i + 1, c.getNome(), c.getEmail(), c.getStatus());
            }
        }
    }

    private void aprovarCandidato() {
        System.out.println("\n--- APROVAR CANDIDATO ---");
        List<Candidato> pendentes = controlador.listarPendentes();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhum candidato pendente para aprovar.");
            return;
        }

        listarPendentes();

        System.out.print("\nDigite o número do candidato para aprovar: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;

            if (indice >= 0 && indice < pendentes.size()) {
                Candidato candidato = pendentes.get(indice);
                
                System.out.print("Digite o ID do representante: ");
                String representanteId = scanner.nextLine();

                boolean sucesso = controlador.aprovarCandidato(candidato.getId(), representanteId);

                if (sucesso) {
                    System.out.println("\n✓ Candidato aprovado com sucesso!");
                } else {
                    System.out.println("\n✗ Erro ao aprovar candidato.");
                }
            } else {
                System.out.println("Número inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido!");
        }
    }
}
