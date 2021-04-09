package com.nagarro.accountstatementserver.service;


import com.nagarro.accountstatementserver.domain.Statement;
import com.nagarro.accountstatementserver.domain.repository.StatementRepository;
import com.nagarro.accountstatementserver.exception.GenericRuntimeException;
import com.nagarro.accountstatementserver.utilities.ServerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StatementService {

    @Autowired
    private StatementRepository repository;

    public List<Statement> getStatementList(Optional<Long> accountId, Optional<String> fromDate,
                                            Optional<String> toDate, Optional<String> fromAmount, Optional<String> toAmount)
            throws GenericRuntimeException {

        if (!fromAmount.isPresent() || !toAmount.isPresent()) {
            log.info("From Amount or To Amount is missing...");

            return this.repository.findBetweenFromDateAndToDateAndForAccountId(fromDate.get(), toDate.get(),
                    accountId.get());
        } else {

            return this.repository.findBetweenFromAmountAndToAmount_AndForAccountId_AndBetweenFromDateAndToDate(
                    fromAmount.get(), toAmount.get(), accountId.get(), fromDate.get(), toDate.get());
        }

    }

    public static boolean isValidStatementDate(Optional<String> date) throws GenericRuntimeException {
        if (date.isPresent()) {
            DateFormat sdf = new SimpleDateFormat(ServerConstants.STATEMENT_DATE_FORMAT);
            sdf.setLenient(false);
            try {
                sdf.parse(date.get());
            } catch (ParseException e) {
                return true;

            }
        }
        return false;
    }

}
