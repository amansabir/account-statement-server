package com.nagarro.accountstatementserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nagarro.accountstatementserver.domain.Account;
import com.nagarro.accountstatementserver.domain.repository.AccountRepository;
import com.nagarro.accountstatementserver.exception.GenericRuntimeException;


@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account findById(Long id) throws GenericRuntimeException {
        Optional<Account> account = repository.findById(id);

        return account.orElseThrow(() -> new GenericRuntimeException("Invalid Account Id", HttpStatus.BAD_REQUEST));
    }

}
