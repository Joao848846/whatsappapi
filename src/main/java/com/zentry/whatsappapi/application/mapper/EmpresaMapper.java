package com.zentry.whatsappapi.application.mapper;

import com.zentry.whatsappapi.adapter.in.controller.companies.dto.EmpresaDTO;
import com.zentry.whatsappapi.domain.model.companies.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaMapper INSTANCE = Mappers.getMapper(EmpresaMapper.class);

    EmpresaDTO toEmpresaDTO(Empresa empresa);
    List<EmpresaDTO> toEmpresaDTO(List<Empresa> empresas);
    Empresa toEmpresa(EmpresaDTO empresaDTO);
}
