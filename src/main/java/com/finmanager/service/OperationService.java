package com.finmanager.service;

import com.finmanager.dao.IDao;
import com.finmanager.dto.OperationDto;
import com.finmanager.dtoMapper.OperationDtoMapper;
import com.finmanager.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService implements IService<OperationDto> {

    private IDao<Operation> operationDao;
    private OperationDtoMapper operationDtoMapper;

    @Autowired
    public OperationService(IDao<Operation> operationDao, OperationDtoMapper operationDtoMapper) {
        this.operationDao = operationDao;
        this.operationDtoMapper = operationDtoMapper;
    }

    @Override
    public OperationDto create(OperationDto operationDto) {
        return operationDtoMapper.operationToOperationDto(operationDao.create(operationDtoMapper.operationDtoToOperation(operationDto)));
    }

    @Override
    public OperationDto findById(Long id) {
        return operationDtoMapper.operationToOperationDto(operationDao.findById(id));
    }

    @Override
    public List<OperationDto> findAll() {
        return operationDao.findAll().stream().map(operationDtoMapper::operationToOperationDto).collect(Collectors.toList());
    }

    @Override
    public OperationDto update(OperationDto operationDto) {
        return operationDtoMapper.operationToOperationDto(operationDao.update(operationDtoMapper.operationDtoToOperation(operationDto)));
    }

    @Override
    public boolean delete(Long id) {
        return operationDao.delete(id);
    }
}
