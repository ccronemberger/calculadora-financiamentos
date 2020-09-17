package com.teste;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * http://www.matematicadidatica.com.br/CalculoPrestacao.aspx
 */
public class Main {
    // O coeficiente de financiamento é um fator que ao ser multiplicado pelo valor a ser financiado,
    // irá nos fornecer o valor de cada prestação.
    public static BigDecimal cf(BigDecimal taxaMensal, int numeroMeses) {
        BigDecimal val = taxaMensal.add(BigDecimal.ONE).pow(numeroMeses);
        val = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(val, 10, RoundingMode.HALF_EVEN));
        return taxaMensal.divide(val, 10, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal valorPresente(BigDecimal valorFuturo, BigDecimal taxaMensal, int numeroMeses) {
        BigDecimal val = taxaMensal.add(BigDecimal.ONE).pow(numeroMeses);
        return valorFuturo.divide(val, 10, RoundingMode.HALF_EVEN);
    }
    // https://maisretorno.com/blog/termos/v/valor-futuro
    // VF = VP (1+i)^n

    public static BigDecimal valorParcelaComBalao(BigDecimal valorBem, BigDecimal taxaMensal, int numeroMeses, BigDecimal residual) {
        BigDecimal valorPresente = valorPresente(residual, taxaMensal, numeroMeses);
        valorBem = valorBem.subtract(valorPresente);
        return cf(taxaMensal,numeroMeses).multiply(valorBem);
    }

    public static void main(String[] args) {
        BigDecimal taxaMensal = new BigDecimal("0.01");

        BigDecimal residual = new BigDecimal("2000");
        BigDecimal valorPresente = valorPresente(residual, taxaMensal, 10);
        System.out.println("valor presente: " + valorPresente);

        BigDecimal valorBem = new BigDecimal("10000").subtract(valorPresente);
        BigDecimal valorParcela = cf(taxaMensal,10).multiply(valorBem);
        System.out.println("valor parcela: " + valorParcela);

        for (int i = 0; i < 5; i++) {
            residual = new BigDecimal(i * 1000);
            System.out.println(residual + " | " + valorParcelaComBalao(new BigDecimal("10000"), taxaMensal, 10, residual));
        }
    }
}
