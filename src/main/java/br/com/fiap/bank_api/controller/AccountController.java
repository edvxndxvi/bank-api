package br.com.fiap.bank_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.bank_api.dto.TransactionDto;
import br.com.fiap.bank_api.model.Account;
import br.com.fiap.bank_api.model.AccountStatus;

@RestController
@RequestMapping("/account")
public class AccountController {
    private Logger log = LoggerFactory.getLogger(getClass());
    private List<Account> repository = new ArrayList<>();

    // Cadastrar conta
    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        System.out.println("Fazendo cadastro de conta..." + account.getTitular());
        try{
            repository.add(account);
            return ResponseEntity.status(200).body(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os dados estão inválidos.");
        }
    }

    // Encerrar Conta
    @PutMapping("/encerrar/{id}")
    public ResponseEntity<Account> closeAccount(@PathVariable Long id) {
        // Buscando a conta no repositório
        Account account = getAccount(id);  // Supondo que getAccount já esteja implementado corretamente
    
        if (account == null) {
            // Se a conta não for encontrada, retorna 404 (NOT FOUND)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    
        log.info("Encerrando conta " + id + " de " + account.getTitular());
    
        // Modificando o status da conta
        account.setAtivo(AccountStatus.INATIVA);  // Marca a conta como inativa
        
        return ResponseEntity.ok(account);  // Retorna a conta com status 200 OK
    }

    
    // Listar todas as contas
    @GetMapping() 
    public List<Account> index() { 
        return repository;
    }
    
    // Buscar conta por ID
    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id){
        log.info("Buscando conta por ID" + id);
        return getAccount(id);
    }

    // Buscar conta por CPF
    @GetMapping("/cpf/{cpf}")
    public Account getByCpf(@PathVariable String cpf){
        log.info("Buscando conta por CPF " + cpf);
        return getAccountByCpf(cpf);
    }

    // Método auxiliar para buscar conta por ID
    private Account getAccount(Long id) {
        return repository
            .stream()
            .filter(c -> c.getId().equals(id)) 
            .findFirst()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );
    }

    // Método auxiliar para buscar conta por CPF
    private Account getAccountByCpf(String cpf) {
        return repository
            .stream()
            .filter(c -> c.getCpf().equals(cpf))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Deposito
    @PatchMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody TransactionDto transactionDto)  {
        log.info("Fazendo depósito de R$" + transactionDto.valor() + " da conta " + transactionDto.contaDestino());

        var contaDestino = getAccount(transactionDto.contaOrigem());
        try {
            contaDestino.deposit(transactionDto.valor());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e);
        }

        return ResponseEntity.status(200).body(contaDestino);
    }

    // Saque
    @PatchMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody TransactionDto transactionDto) {
        log.info("Sacando R$" + transactionDto.valor() + " da conta " + transactionDto.contaOrigem());

        var contaOrigem = getAccount(transactionDto.contaOrigem());
        try {
            contaOrigem.withdraw(transactionDto.valor());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e);
        }

        return ResponseEntity.status(200).body(contaOrigem);
    }

    // Pix
    @PutMapping("/pix")
    public ResponseEntity<?> createPix (@RequestBody TransactionDto dto) {
        log.info("Fazendo Pix da conta " + dto.contaOrigem() + " para a conta " + dto.contaDestino());

        var contaOrigem = getAccount(dto.contaOrigem());
        var contaDestino = getAccount(dto.contaDestino());
        try {
            contaOrigem.withdraw(dto.valor());
            contaDestino.deposit(dto.valor());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e);
        }

        return ResponseEntity.status(200).body(contaOrigem);
    }


}
