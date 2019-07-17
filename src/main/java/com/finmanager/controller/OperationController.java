package com.finmanager.controller;

import com.finmanager.dto.OperationDto;
import com.finmanager.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {
    private IService<OperationDto> operationService;

    @Autowired
    public OperationController(IService<OperationDto> operationService) {
        this.operationService = operationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public OperationDto createOperation(@RequestBody OperationDto operationDto) {
        return operationService.create(operationDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationDto findOperationById(@PathVariable("id") Long id) {
        return operationService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OperationDto> findAllOperations() {
        return operationService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OperationDto updateOperation(@PathVariable("id") Long id, @RequestBody OperationDto operationDto) {
        operationDto.setId(id);
        return operationService.update(operationDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteOperation(@PathVariable("id") Long id) {
        return operationService.delete(id);
    }
}
