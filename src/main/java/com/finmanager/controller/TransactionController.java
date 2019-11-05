package com.finmanager.controller;

import com.finmanager.dto.TransactionDto;
import com.finmanager.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private ITransactionService transactionService;

    @Autowired
    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public TransactionDto createTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.create(transactionDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDto findTransactionById(@PathVariable("id") Long id) {
        return transactionService.findById(id);
    }

    @GetMapping(value = "/categoryId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> findTransactionByCategoryId(@PathVariable("id") Long id) {
        return transactionService.getTransactionByCategoryId(id);
    }

    @GetMapping(value = "/operationId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> findTransactionByOperationId(@PathVariable("id") Long id) {
        return transactionService.getTransactionByOperationId(id);
    }

    @GetMapping(value = "/userId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> findTransactionByUserId(@PathVariable("id") Long id) {
        return transactionService.getTransactionByUserId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> findAllTransactions() {
        return transactionService.findAll();
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDto updateTransaction(@PathVariable("id") Long id, @RequestBody TransactionDto transactionDto) {
        transactionDto.setId(id);
        return transactionService.update(transactionDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public boolean deleteTransaction(@PathVariable("id") Long id) {
        return transactionService.delete(id);
    }
}
