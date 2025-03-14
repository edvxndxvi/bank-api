package br.com.fiap.bank_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MembersController {
    
    @GetMapping()
    public String index() {
        return """
            Nome do Projeto: Bank API
            Integrantes:
            Edvan Davi - RM554733   
            Rafael Romanini - RM554637
        """;
    }
}
