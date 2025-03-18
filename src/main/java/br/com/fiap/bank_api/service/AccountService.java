package br.com.fiap.bank_api.service;

import br.com.fiap.bank_api.model.Account;
import br.com.fiap.bank_api.model.AccountStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final List<Account> repository = new ArrayList<>();

    public Account createAccount(Account account) { 
        long newId = repository.size() + 1;  
        account.setId(newId);
        
        repository.add(account);  
        return account;  
    }

    public Account getById(Long id) {
        return repository
            .stream()
            .filter(c -> c.getId() != null && c.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada."));
    }

    public Account getByCpf(String cpf) {
        return repository
            .stream()
            .filter(c -> c.getCpf() != null && c.getCpf().equals(cpf))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void closeAccount(Long id) {
        Account account = getById(id);
        account.setActive(AccountStatus.N);
    }

    public void deposit(Long id, double value) {
        Account account = getById(id);
        account.deposit(value);
    }

    public void withdraw(Long id, double value) {
        Account account = getById(id);
        account.withdraw(value);
    }

    public void transfer(Long origem, Long destino, double value) {
        Account contaOrigem = getById(origem);
        Account contaDestino = getById(destino);

        contaOrigem.withdraw(value);
        contaDestino.deposit(value);
    }

    public List<Account> getAllAccounts() {
        return repository;
    }
}