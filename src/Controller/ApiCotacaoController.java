package Controller;

import Model.Cotacao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCotacaoController {
    private static final String BASE_URL = "https://economia.awesomeapi.com.br/json/last/";

    public Cotacao consultarCotacao(String moeda) {
        try {
            // Formatar a moeda para o padrão da API (ex: USD-BRL)
            String moedaFormatada = moeda.toUpperCase() + "-BRL";
            String urlString = BASE_URL + moedaFormatada;

            // Fazer requisição HTTP
            String jsonResponse = fazerRequisicao(urlString);

            // Parse do JSON
            return parseJsonResponseManual(jsonResponse, moeda.toUpperCase());

        } catch (Exception e) {
            System.err.println("Erro ao consultar cotação: " + e.getMessage());
            return null;
        }
    }

    private String fazerRequisicao(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            throw new RuntimeException("Erro HTTP: " + responseCode);
        }
    }

    private Cotacao parseJsonResponseManual(String jsonResponse, String moeda) {
        try {
            // Remover chaves externas
            String json = jsonResponse.trim();

            // Encontrar o objeto da moeda específica
            String chaveBusca = "\"" + moeda + "BRL\"";
            int startIndex = json.indexOf(chaveBusca);

            if (startIndex == -1) {
                throw new RuntimeException("Moeda " + moeda + " não encontrada na resposta da API");
            }

            // Encontrar o início do objeto { após a chave
            startIndex = json.indexOf("{", startIndex);
            int endIndex = findMatchingBrace(json, startIndex);

            if (endIndex == -1) {
                throw new RuntimeException("Formato JSON inválido");
            }

            String moedaJson = json.substring(startIndex, endIndex + 1);

            // Extrair valores manualmente
            String code = extractStringValue(moedaJson, "code");
            double bid = extractDoubleValue(moedaJson, "bid");
            double ask = extractDoubleValue(moedaJson, "ask");
            double pctChange = extractDoubleValue(moedaJson, "pctChange");

            return new Cotacao(code, bid, ask, pctChange);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar resposta: " + e.getMessage());
        }
    }

    private int findMatchingBrace(String json, int startIndex) {
        int count = 0;
        for (int i = startIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') count++;
            else if (c == '}') count--;

            if (count == 0) return i;
        }
        return -1;
    }

    private String extractStringValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return "";

        startIndex += searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }

    private double extractDoubleValue(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return 0.0;

        startIndex += searchKey.length();
        int endIndex = json.indexOf("\"", startIndex);
        String valueStr = json.substring(startIndex, endIndex);

        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Método para listar moedas disponíveis
    public String[] getMoedasDisponiveis() {
        return new String[] {
                "USD", "EUR", "GBP", "JPY", "CHF", "CAD", "AUD", "CNY", "BTC", "ETH"
        };
    }
}