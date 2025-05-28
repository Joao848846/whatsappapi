package com.zentry.whatsappapi.adapter.in.controller.companies;

// Importa o DTO usado para transferência de dados da empresa
import com.zentry.whatsappapi.adapter.in.controller.companies.dto.EmpresaDTO;
// Importa o serviço que contém a lógica de criação de empresas
import com.zentry.whatsappapi.application.service.companies.EmpresaService;

import com.zentry.whatsappapi.domain.model.companies.Empresa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Define que essa classe é um Controller REST
@RestController
// Define o path base para todas as rotas dessa controller
@RequestMapping("/api/v1/companies")
public class EmpresaController {

    private final EmpresaService empresaService;

    // Injeção de dependência via construtor — prática recomendada, especialmente com @Service
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Endpoint POST empresa
    @PostMapping("/create")
    public ResponseEntity<EmpresaDTO> criarEmpresa(@RequestBody EmpresaDTO empresasDTO) {
        // Chama o serviço que contém a lógica de criação
        EmpresaDTO empresaCriada = empresaService.criarEmpresa(empresasDTO);
        // Retorna a empresa criada com o status HTTP 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaCriada);
    }

    // Endpoint GET para verificar se a API está funcionando
    @GetMapping()
    public ResponseEntity<List<EmpresaDTO>> listarEmpresas() {
        List<Empresa> empresas = empresaService.ListarEmpresas();
        List<EmpresaDTO> empresasDTO = empresas.stream()
                .map(EmpresaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(empresasDTO);
    }
}
