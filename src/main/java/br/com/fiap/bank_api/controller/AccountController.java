package br.com.fiap.bank_api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.bank_api.dto.TransactionDto;
import br.com.fiap.bank_api.model.Account;
import br.com.fiap.bank_api.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private Logger log = LoggerFactory.getLogger(getClass());

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Cadastrar conta
    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        System.out.println("Fazendo cadastro de conta..." + account.getHolder());
        try{
            return ResponseEntity.ok(accountService.createAccount(account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao criar conta: " + e.getMessage());
        }
    }

    // Encerrar Conta
    @PutMapping("/close/{id}")
    public ResponseEntity<?> closeAccount(@PathVariable Long id) {
        log.info("Encerrando conta ", id);
       
        try{
            accountService.closeAccount(id);
            return ResponseEntity.ok("Conta encerrada");
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Conta não encontrada.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("A conta já está inativa.");
        }
    }

    
    // Listar todas as contas
    @GetMapping() 
    public ResponseEntity<List<Account>> index() { 
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    
    // Buscar conta por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        log.info("Buscando conta por ID" + id);
        try {
            return ResponseEntity.ok(accountService.getById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        }
    }

    // Buscar conta por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getByCpf(@PathVariable String cpf){
        log.info("Buscando conta por CPF" + cpf);
        try {
            return ResponseEntity.ok(accountService.getByCpf(cpf));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        }
    }

    
    // Deposito
    @PatchMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionDto transactionDto)  {
        log.info("Fazendo depósito de R$" + transactionDto.valor() + " da conta " + transactionDto.contaDestino());

        try {
            accountService.deposit(transactionDto.contaDestino(), transactionDto.valor());
            return ResponseEntity.ok("Depósito realizado com sucesso.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Valor inválido para depósito.");
        }
    }

    // Saque
    @PatchMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody TransactionDto transactionDto) {
        log.info("Sacando R${} da conta {}", transactionDto.valor(), transactionDto.contaOrigem());
        try {
            accountService.withdraw(transactionDto.contaOrigem(), transactionDto.valor());
            return ResponseEntity.ok("Saque realizado com sucesso.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Saldo insuficiente ou valor inválido.");
        }
    }

    // Pix
    @PutMapping("/pix")
    public ResponseEntity<?> createPix (@RequestBody TransactionDto dto) {
        log.info("Transferindo via PIX da conta {} para {}", dto.contaOrigem(), dto.contaDestino());
        try {
            accountService.transfer(dto.contaOrigem(), dto.contaDestino(), dto.valor());
            return ResponseEntity.ok("Pix realizada com sucesso.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Uma das contas não foi encontrada.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Saldo insuficiente ou valor inválido.");
        }
    }


}
