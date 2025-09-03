package com.zentry.whatsappapi.application.service.companies;

import com.zentry.whatsappapi.adapter.in.controller.companies.dto.EmpresaDTO;
import com.zentry.whatsappapi.domain.model.companies.Empresa;
import com.zentry.whatsappapi.infrastructure.Repository.EmpresaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.zentry.whatsappapi.application.mapper.EmpresaMapper;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    public EmpresaDTO criarEmpresa(EmpresaDTO dto) {

        Empresa empresa = empresaMapper.toEmpresa(dto);
        empresaMapper.toEmpresa(dto);

        try {
            Empresa salva = empresaRepository.save(empresa);
            return new EmpresaDTO();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao salvar empresa. Verifique se os dados estão corretos.");
        }
    }

    public List<Empresa> ListarEmpresas() {
        // Retorna todas as empresas do banco de dados
        return empresaRepository.findAll();
    }
}
