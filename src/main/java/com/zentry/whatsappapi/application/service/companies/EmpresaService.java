package com.zentry.whatsappapi.application.service.companies;

import com.zentry.whatsappapi.adapter.in.controller.companies.dto.EmpresaDTO;
import com.zentry.whatsappapi.domain.model.companies.Empresa;
import com.zentry.whatsappapi.infrastructure.Repository.EmpresaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public EmpresaDTO criarEmpresa(EmpresaDTO dto) {
        Empresa empresa = new Empresa();

        // ‘Log’ para verificar o valor recebido
        System.out.println("Nome do responsável recebido: " + dto.getNomeResponsavel());

        if (dto.getCpf() != null && !dto.getCpf().isEmpty()) {
            empresa.setCpf(dto.getCpf());
        }

        if (dto.getCnpj() != null && !dto.getCnpj().isEmpty()) {
            empresa.setCnpj(dto.getCnpj());
        }

        empresa.setNome(dto.getNome());
        empresa.setNomeResponsavel(dto.getNomeResponsavel());
        empresa.setEmailResponsavel(dto.getEmailResponsavel());
        empresa.setEstadoPagamento(dto.getEstadoPagamento());
        empresa.setPlanoContratado(dto.getPlanoContratado());

        try {
            Empresa salva = empresaRepository.save(empresa);
            return new EmpresaDTO(salva);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao salvar empresa. Verifique se os dados estão corretos.");
        }
    }

    public List<Empresa> ListarEmpresas() {
        // Retorna todas as empresas do banco de dados
        return empresaRepository.findAll();
    }
}
