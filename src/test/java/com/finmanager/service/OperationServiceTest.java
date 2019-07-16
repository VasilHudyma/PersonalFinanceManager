package com.finmanager.service;

import com.finmanager.dao.IDao;
import com.finmanager.dto.OperationDto;
import com.finmanager.dtoMapper.OperationDtoMapper;
import com.finmanager.model.Operation;
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

    private final OperationDto operationDto = new OperationDto(1L, "oper", LocalDateTime.now(), LocalDateTime.now());
    private OperationDtoMapper operationDtoMapper = new OperationDtoMapper();

    @Mock
    private IDao<Operation> operationDao;

    @InjectMocks
    private OperationService operationService;

    private Operation operation;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        operationService = new OperationService(operationDao, operationDtoMapper);
        operation = operationDtoMapper.operationDtoToOperation(operationDto);
    }

    @Test
    void createOperationTest() {
        doReturn(operation).when(operationDao).create(operation);
        OperationDto resultOperationDto = operationService.create(operationDto);
        assertEquals(resultOperationDto, operationDtoMapper.operationToOperationDto(operation));
    }

    @Test
    void findByIdOperationTest() {
        doReturn(operation).when(operationDao).findById(operation.getId());
        OperationDto result = operationService.findById(operation.getId());
        assertEquals(operationDtoMapper.operationToOperationDto(operation), result);
    }

    @Test
    void findAllOperationsTest() {
        List<Operation> operations = Arrays.asList(operation, new Operation(2L, "secOper", LocalDateTime.now(), LocalDateTime.now()));
        doReturn(operations).when(operationDao).findAll();

        List<OperationDto> operationDtos = operationService.findAll();

        for (int i = 0; i < operations.size(); i++) {
            assertEquals(operations.get(i), operationDtoMapper.operationDtoToOperation(operationDtos.get(i)));
        }
    }

    @Test
    void updateOperation() {
        doReturn(operation).when(operationDao).update(operation);
        OperationDto operationDto1 = operationService.update(operationDto);
        assertEquals(operation, operationDtoMapper.operationDtoToOperation(operationDto1));
    }

    @Test
    void deleteOperation() {
        doReturn(true).when(operationDao).delete(operation.getId());
        assertTrue(operationService.delete(operationDto.getId()));
    }
}
