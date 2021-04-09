package com.nagarro.accountstatementserver.domain.repository;

import com.nagarro.accountstatementserver.domain.Statement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StatementRepositoryTest {

    @Autowired
    private StatementRepository statementRepository;

    @Test
    void shouldReturnStatementWhenSearchedById() {

        Optional<Statement> statement = statementRepository.findById(10001L);

        assertThat(statement
                .orElseGet(Statement::new)
                .getAccountId()).isEqualTo(1201L);
    }

    @Test
    void shouldReturnStatementsAfterSpecifiedDate() {
        List<Statement> statement = statementRepository.findAfterDate("2021-04-02");

        assertThat(statement
                .get(0)
                .getAccountId()).isEqualTo(1201L);
    }

    @Test
    void shouldReturnStatementForGivenAccountNumber() {

        List<Statement> statementList = statementRepository.findByAccountId(1202L);

        assertThat(statementList
                .get(0)
                .getDate()).isEqualTo("2021-04-02");
    }

    @Test
    void shouldReturnStatementForGivenFromDateAndToDate() {

        String fromDate = "2021-01-01";
        String toDate = "2021-01-03";

        List<Statement> statementList = statementRepository.findBetweenFromDateAndToDateAndForAccountId(fromDate,
                toDate, 1201L);

        assertThat(statementList.size()).isEqualTo(3);
    }

    @Test
    void shouldReturnStatementForGivenFromAmountAndToAmount() {
        String fromAmount = "8000";
        String toAmount = "22000";

        List<Statement> statementList = statementRepository.findBetweenFromAmountAndToAmountAndForAccountId(fromAmount,
                toAmount, 1201L);

        assertThat(statementList.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnStatementForGiven_FromAmountAndToAmount_And_FromDateAndToDate() {

        String fromDate = "2020-12-01";
        String toDate = "2021-12-31";
        String fromAmount = "8000";
        String toAmount = "40000";

        List<Statement> statementList = statementRepository
                .findBetweenFromAmountAndToAmount_AndForAccountId_AndBetweenFromDateAndToDate(fromAmount, toAmount,
                        1201L, fromDate, toDate);

        assertThat(statementList.size()).isEqualTo(5);
    }

}
