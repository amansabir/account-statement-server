package com.nagarro.accountstatementserver.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nagarro.accountstatementserver.domain.Account;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testSaveNewAccounts() {

        Optional<Account> account = accountRepository.findById(1201L);

        assertThat(account
                .get()
                .getAccountNumber()).isEqualTo("123456723");
    }

    @Test
    void shouldReturnAfterCreatedDate() {

        Optional<Account> account = accountRepository.findById(1202L);

        assertThat(account
                .get()
                .getAccountNumber()).isEqualTo("123456724");
    }

}
