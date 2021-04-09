package com.nagarro.accountstatementserver.controller;

import com.nagarro.accountstatementserver.domain.Role;
import com.nagarro.accountstatementserver.exception.GenericRuntimeException;
import com.nagarro.accountstatementserver.service.AccountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.nagarro.accountstatementserver.service.StatementService.isValidStatementDate;

@RestController
@RequestMapping("/api/account/")
public class AccountStatementRestController {

    @Autowired
    private AccountStatementService accountStatementService;

    @GetMapping("{accountId}/statement")
    public ResponseEntity<List<Map<String, Object>>> getStatements(@PathVariable("accountId") Optional<Long> accountId,
                                                                   @RequestParam Optional<String> fromDate, @RequestParam Optional<String> toDate,
                                                                   @RequestParam Optional<String> fromAmount, @RequestParam Optional<String> toAmount)
            throws GenericRuntimeException {

        validateStatementFetchRequest(fromDate, toDate, fromAmount, toAmount);

        if (isValidStatementDate(toDate) || isValidStatementDate(fromDate)) {
            throw new GenericRuntimeException("Statement Date Format is not valid. Supported format - 'YYYY-MM-dd'",
                    HttpStatus.BAD_REQUEST);

        }
        List<Map<String, Object>> statementList = this.accountStatementService.getAccountStatement(accountId, fromDate,
                toDate, fromAmount, toAmount);

        return ResponseEntity.of(Optional.ofNullable(statementList));
    }

    private void validateStatementFetchRequest(@RequestParam Optional<String> fromDate,
                                               @RequestParam Optional<String> toDate, @RequestParam Optional<String> fromAmount,
                                               @RequestParam Optional<String> toAmount) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication != null
                && authentication
                .getAuthorities()
                .stream()
                .anyMatch(a -> a
                        .getAuthority()
                        .equals(Role.USER.name())) &&
                (fromDate.isPresent() || toDate.isPresent() || fromAmount.isPresent() || toAmount.isPresent())) {
            throw new GenericRuntimeException("User cannot specify search parameters.", HttpStatus.UNAUTHORIZED);
        }

    }

}
