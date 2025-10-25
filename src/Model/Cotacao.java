package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cotacao {
    private String codigoMoeda;
    private double valorCompra;
    private double valorVenda;
    private double variacao;
    private LocalDateTime dataHoraConsulta;

    public Cotacao(String codigoMoeda, double valorCompra, double valorVenda, double variacao) {
        this.codigoMoeda = codigoMoeda;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;
        this.variacao = variacao;
        this.dataHoraConsulta = LocalDateTime.now();
    }

    // Getters
    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public double getVariacao() {
        return variacao;
    }

    public LocalDateTime getDataHoraConsulta() {
        return dataHoraConsulta;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format(
                "Cotação %s/BRL\n" +
                        "Compra: R$ %.4f\n" +
                        "Venda: R$ %.4f\n" +
                        "Variação: %.2f%%\n" +
                        "Consulta: %s",
                codigoMoeda, valorCompra, valorVenda, variacao,
                dataHoraConsulta.format(formatter)
        );
    }
}