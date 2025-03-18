package br.com.fiap.bank_api.dto;

public record TransactionDto(
    Long contaOrigem,
    Long contaDestino,
    double valor
    ) {
}