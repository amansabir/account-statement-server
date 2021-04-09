package com.nagarro.accountstatementserver.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nagarro.accountstatementserver.domain.Account;
import com.nagarro.accountstatementserver.domain.Statement;
import com.nagarro.accountstatementserver.exception.GenericRuntimeException;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.hash.Hashing.sha256;
import static com.nagarro.accountstatementserver.utilities.ServerConstants.STATEMENT_DATE_FORMATTER;

@Service
@Slf4j
public class AccountStatementService {

    @Autowired
    private StatementService statementService;

    @Autowired
    private AccountService accountService;

    public List<Map<String, Object>> getAccountStatement(Optional<Long> accountId, Optional<String> fromDate,
                                                         Optional<String> toDate, Optional<String> fromAmount, Optional<String> toAmount) throws GenericRuntimeException {

        Account account = this.accountService.findById(accountId.orElseThrow(() -> new GenericRuntimeException("Invalid Account Id",
                HttpStatus.BAD_REQUEST)));

        if (!fromDate.isPresent()) {
            log.info("From date is missing, defaulting to current date..");
            fromDate = Optional.ofNullable(LocalDate
                    .now()
                    .format(STATEMENT_DATE_FORMATTER));
        }

        if (!toDate.isPresent()) {
            log.info("To date is missing, defaulting to date after 3 months.");
            toDate = Optional.ofNullable(LocalDate
                    .now()
                    .plusMonths(3)
                    .format(STATEMENT_DATE_FORMATTER));
        }

        List<Statement> statementList = this.statementService.getStatementList(accountId, fromDate, toDate, fromAmount,
                toAmount);

        return getEnrichedStatement(account, statementList);

    }

    private List<Map<String, Object>> getEnrichedStatement(final Account account, final List<Statement> statementList) {

        return statementList
                .stream()
                .map(statement -> {

                    Map<String, Object> objectMap = new HashMap<>();
                    String hashedAccountNumber = String
                            .valueOf(sha256().hashString(account.getAccountNumber(), StandardCharsets.UTF_8));
                    objectMap.put("AccountNumber", hashedAccountNumber);
                    objectMap.put("Date", statement.getDate());
                    objectMap.put("Account Type", account.getAccountType());
                    objectMap.put("Amount", statement.getAmount());

                    return objectMap;

                })
                .collect(Collectors.toList());

    }

}
