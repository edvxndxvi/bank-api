package br.com.fiap.bank_api.model;

import java.time.LocalDate;
import java.util.Random;

public class Account {
    private Long id;
    private Long numero;
    private int agencia;
    private String titular;
    private String cpf;
    private LocalDate dataAbertura;
    private double saldoInicial;
    private String ativo;
    private AccountType tipo;

    // Constructor
    public Account(Long id, Long numero, int agencia, String titular, String cpf, LocalDate dataAbertura, double saldoInicial, String ativo, AccountType tipo) {
        this.id = (id == null) ? Math.abs(new Random().nextLong()) : id;
        this.numero = numero;
        this.agencia = agencia;
        this.titular = titular;
        this.cpf = cpf;
        this.dataAbertura = dataAbertura;
        this.saldoInicial = saldoInicial;
        this.ativo = ativo;
        this.tipo = tipo;
    }

    public Account() {
    }

    // Getters And Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String isAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public AccountType getTipo() {
        return tipo;
    }

    public void setTipo(AccountType tipo) {
        this.tipo = tipo;
    }

    // Metods
    public void validateAccount() {
        if (titular == null || titular.isEmpty())
            throw new IllegalArgumentException("Titular não pode ser nulo ou vazio");
        if (cpf == null || cpf.isEmpty())
            throw new IllegalArgumentException("Cpf não pode ser nulo ou vazio");
        if (dataAbertura == null || dataAbertura.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de abertura não pode ser nula");
        if (saldoInicial < 0)
            throw new IllegalArgumentException("Saldo não pode ser negativo");
        if (ativo == null || ativo.isEmpty())
            throw new IllegalArgumentException("Ativo não pode ser nulo ou vazio");
    }
}
