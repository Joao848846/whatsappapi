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

    // Método principal para salvar a empresa no banco
    public EmpresaDTO criarEmpresa(EmpresaDTO dto) {
        Empresa empresa = new Empresa();

        // Se veio CPF, ignora o CNPJ, e vice-versa
        if (dto.getCpf() != null && !dto.getCpf().isEmpty()) {
            empresa.setCpf(dto.getCpf());
            empresa.setCnpj(null);
        } else if (dto.getCnpj() != null && !dto.getCnpj().isEmpty()) {
            empresa.setCnpj(dto.getCnpj());
            empresa.setCpf(null);
        } else {

            throw new IllegalArgumentException("Deve ser fornecido pelo menos um CPF ou CNPJ.");
        }

        // Validação de dados obrigatórios
        if (dto.getNomeResponsavel() != null && dto.getNomeResponsavel().isEmpty()) {
            empresa.setNomeResponsavel(dto.getNomeResponsavel());

            throw new IllegalArgumentException("Nome do responsável não pode ser vazio.");
        }
        if (dto.getEmailResponsavel() == null || dto.getEmailResponsavel().isEmpty()) {
            throw new IllegalArgumentException("E-mail do responsável não pode ser vazio.");
        }

        // Preenche os dados da empresa com os dados do DTO
        empresa.setNome(dto.getNome());
        empresa.setEmailResponsavel(dto.getEmailResponsavel());
        // Define o estado do pagamento como "aguardando pagamento" (0)
        empresa.setEstadoPagamento(0);
        empresa.setPlanoContratado(dto.getPlanoContratado());

        try {
            Empresa salva = empresaRepository.save(empresa);
            return new EmpresaDTO(salva); // Retorna DTO da empresa salva
        } catch (DataIntegrityViolationException e) {
            // Se ocorrer algum erro ao salvar, como violação de chave única, lançar uma exceção
            throw new RuntimeException("Erro ao salvar empresa. Verifique se os dados estão corretos.");
        }
    }

    public List<Empresa> ListarEmpresas() {
        // Retorna todas as empresas do banco de dados
        return empresaRepository.findAll();
    }
}
