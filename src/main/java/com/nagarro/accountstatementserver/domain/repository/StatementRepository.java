package com.nagarro.accountstatementserver.domain.repository;


import com.nagarro.accountstatementserver.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Long>, JpaSpecificationExecutor<Statement> {

    @Query(value = "select * from statement where to_date(date,'YYYY-MM-DD') > to_date(:fromDate,'YYYY-MM-DD')", nativeQuery = true)
    List<Statement> findAfterDate(String fromDate);

    List<Statement> findByAccountId(Long accountNumber);

    @Query(value = "select * from statement where account_id=:accountId and to_date(date,'YYYY-MM-DD')  between to_date(:fromDate,'YYYY-MM-DD') and to_date(:toDate,'YYYY-MM-DD')", nativeQuery = true)
    List<Statement> findBetweenFromDateAndToDateAndForAccountId(String fromDate, String toDate,
                                                                Long accountId);

    @Query(value = "select * from statement where account_id=:accountId and to_number(amount) between to_number(:fromAmount) and to_number(:toAmount)", nativeQuery = true)
    List<Statement> findBetweenFromAmountAndToAmountAndForAccountId(String fromAmount, String toAmount,
                                                                    Long accountId);

    @Query(value = "select * from statement where account_id=:accountId and to_number(amount) between to_number(:fromAmount) and to_number(:toAmount) and to_date(date,'YYYY-MM-DD') between to_date(:fromDate,'YYYY-MM-DD') and to_date(:toDate,'YYYY-MM-DD')", nativeQuery = true)
    List<Statement> findBetweenFromAmountAndToAmount_AndForAccountId_AndBetweenFromDateAndToDate(String fromAmount,
                                                                                                 String toAmount, Long accountId, String fromDate, String toDate);

}
