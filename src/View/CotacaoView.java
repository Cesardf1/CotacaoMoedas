package View;

import Model.Cotacao;
import Controller.ApiCotacaoController;
import java.util.Scanner;

public class CotacaoView {
    private ApiCotacaoController controller;
    private Scanner scanner;

    public CotacaoView() {
        this.controller = new ApiCotacaoController();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("=== SISTEMA DE COTAÇÃO DE MOEDAS ===");
        System.out.println("Moedas disponíveis: USD, EUR, GBP, JPY, CHF, CAD, AUD, CNY, BTC, ETH");
        System.out.println("Digite 'sair' para encerrar o programa");
        System.out.println("--------------------------------------");
    }

    public void iniciar() {
        exibirMenu();

        while (true) {
            System.out.print("\nDigite o código da moeda (ex: USD): ");
            String codigoMoeda = scanner.nextLine().trim();

            if (codigoMoeda.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando o programa...");
                break;
            }

            if (codigoMoeda.isEmpty()) {
                System.out.println("Por favor, digite um código de moeda válido.");
                continue;
            }

            consultarEExibirCotacao(codigoMoeda);
        }

        scanner.close();
    }

    private void consultarEExibirCotacao(String codigoMoeda) {
        try {
            System.out.println("\nConsultando cotação...");

            Cotacao cotacao = controller.consultarCotacao(codigoMoeda);

            if (cotacao != null) {
                System.out.println("\n" + "=".repeat(40));
                System.out.println(cotacao);
                System.out.println("=".repeat(40));
            } else {
                System.out.println("Não foi possível obter a cotação para " + codigoMoeda);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            System.out.println("Verifique se o código da moeda está correto e tente novamente.");
        }
    }
}