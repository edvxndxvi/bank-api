package br.com.fiap.bank_api.model;

import java.time.LocalDate;
import java.util.Random;

public class Account {
    private Long id;
    private Long number;
    private int agency;
    private String holder;
    private String cpf;
    private LocalDate openingDate;
    private double openingBalance;
    private AccountStatus active;
    private AccountType type;

    // Constructor
    public Account() {}
    
    public Account(Long id,Long number, int agency, String holder, String cpf, LocalDate openingDate, double openingBalance, AccountStatus active, AccountType type) {
        this.id = id;
        this.number = number;
        this.agency = agency;
        setHolder(holder);
        setCpf(cpf);
        setOpeningDate(openingDate);
        setOpeningBalance(openingBalance);
        this.active = AccountStatus.S;
        setType(type);
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
    
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        if (holder == null || holder.isEmpty()) {
            throw new IllegalArgumentException("Titular não pode ser nulo ou vazio");
        } else {
            this.holder = holder;
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

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        if (openingDate == null || openingDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de abertura não pode ser nula nem posterior à data atual.");
        } else {
            this.openingDate = openingDate;
        }
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Saldo não pode ser negativo");
        } else {
            this.openingBalance = openingBalance;
        }
    }

    public AccountStatus isActive() {
        return active;
    }

    public void setActive(AccountStatus active) {
        this.active = active;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    // Methods

    public void deposit(double value) {
        if (value <= 0){
            throw new IllegalArgumentException("Valor do depósito deve ser maior que zero");
        }
        openingBalance += value;
    }

    public void withdraw(double value) {
        if (value <= 0)
            throw new IllegalArgumentException("Valor do saque deve ser maior que zero");
        if (openingBalance < value)
            throw new IllegalArgumentException("Saldo insuficiente");
        openingBalance -= value;
    }
    
}
