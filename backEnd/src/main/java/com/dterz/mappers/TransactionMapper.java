package com.dterz.mappers;

import com.dterz.dtos.TransactionDTO;
import com.dterz.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mappings({
            @Mapping(target = "userName", source = "user.userName"),
            @Mapping(target = "accountName", source = "account.description"),
    })
    TransactionDTO entityToDto(Transaction entity);

    Transaction dtoToEntity(TransactionDTO dto);

    void dtoToEntity(TransactionDTO dto, @MappingTarget Transaction entity);

    List<TransactionDTO> entityListToDTOList(List<Transaction> entitylist);
}
