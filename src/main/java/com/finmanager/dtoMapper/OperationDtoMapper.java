package com.finmanager.dtoMapper;

import com.finmanager.dto.OperationDto;
import com.finmanager.model.Operation;
import org.springframework.stereotype.Component;

@Component
public class OperationDtoMapper {

    public OperationDto operationToOperationDto(Operation operation) {
        return new OperationDto(operation.getId(), operation.getName(), operation.getCreatedDate(), operation.getUpdatedDate());
    }

    public Operation operationDtoToOperation(OperationDto operationDto) {
        return new Operation(operationDto.getId(), operationDto.getName(), operationDto.getCreatedDate(), operationDto.getUpdatedDate());
    }
}
