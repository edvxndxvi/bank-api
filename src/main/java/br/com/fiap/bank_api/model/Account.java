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
    private AccountStatus ativo;
    private AccountType tipo;

    // Constructor
    public Account(Long id, Long numero, int agencia, String titular, String cpf, LocalDate dataAbertura, double saldoInicial, AccountStatus ativo, AccountType tipo) {
        this.id = id;
        this.numero = numero;
        this.agencia = agencia;
        setTitular(titular);
        setCpf(cpf);
        setDataAbertura(dataAbertura);
        setSaldoInicial(saldoInicial);
        this.ativo = AccountStatus.ATIVA;
        setTipo(tipo);
    }

    public Account() {
    }

    // Getters And Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            this.id = Math.abs(new Random().nextLong());
        } else {
            this.id = id;
        }
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
        if (titular == null || titular.isEmpty()) {
            throw new IllegalArgumentException("Titular não pode ser nulo ou vazio");
        } else {
            this.titular = titular;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("Cpf não pode ser nulo ou vazio");
        } else {
            this.cpf = cpf;
        }
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        if (dataAbertura == null || dataAbertura.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de abertura não pode ser nula");
        } else {
            this.dataAbertura = dataAbertura;
        }
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("Saldo não pode ser negativo");
        } else {
            this.saldoInicial = saldoInicial;
        }
    }

    public AccountStatus isAtivo() {
        return ativo;
    }

    public void setAtivo(AccountStatus ativo) {
        this.ativo = ativo;
    }

    public AccountType getTipo() {
        return tipo;
    }

    public void setTipo(AccountType tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de conta não pode ser nulo");
        } else {
            this.tipo = tipo;
        }
    }

    // Methods

    public void deposit(double value) {
        if (value <= 0)
            throw new IllegalArgumentException("Valor do depósito deve ser maior que zero");
        saldoInicial += value;
    }

    public void withdraw(double value) {
        if (value <= 0)
            throw new IllegalArgumentException("Valor do saque deve ser maior que zero");
        if (saldoInicial < value)
            throw new IllegalArgumentException("Saldo insuficiente");
        saldoInicial -= value;
    }
    
}
