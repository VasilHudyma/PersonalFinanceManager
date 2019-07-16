package com.finmanager.dtoMapper;

import com.finmanager.config.ProfileConfig;
import com.finmanager.dto.OperationDto;
import com.finmanager.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProfileConfig.class})
class OperationDtoMapperTest {

    private Operation operation;
    private OperationDto operationDto;


    @Autowired
    private OperationDtoMapper operationDtoMapper;

    @BeforeEach
    void init() {
        operation = new Operation(1L, "oper", LocalDateTime.now(), LocalDateTime.now());
        operationDto = new OperationDto();
        operationDto.setId(operation.getId());
        operationDto.setName(operation.getName());
        operationDto.setCreatedDate(operation.getCreatedDate());
        operationDto.setUpdatedDate(operation.getUpdatedDate());
    }

    @Test
    void operationToOperationDtoTest() {
        assertEquals(operationDto, operationDtoMapper.operationToOperationDto(operation));
    }

    @Test
    void operationDtoToOperationTest() {
        assertEquals(operation, operationDtoMapper.operationDtoToOperation(operationDto));
    }
}
