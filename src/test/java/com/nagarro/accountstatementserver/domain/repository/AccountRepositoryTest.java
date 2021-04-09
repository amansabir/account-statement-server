package com.nagarro.accountstatementserver.domain.repository;

import com.nagarro.accountstatementserver.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testSaveNewAccounts() {

        Optional<Account> account = accountRepository.findById(1201L);

        assertThat(account
                .orElseGet(Account::new)
                .getAccountNumber()).isEqualTo("123456723");
    }

    @Test
    void shouldReturnAfterCreatedDate() {

        Optional<Account> account = accountRepository.findById(1202L);

        assertThat(account
                .orElseGet(Account::new)
                .getAccountNumber()).isEqualTo("123456724");
    }

}
