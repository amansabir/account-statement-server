package com.nagarro.accountstatementserver.utilities;

import org.apache.catalina.Server;

import java.time.format.DateTimeFormatter;

public class ServerConstants {

    private ServerConstants() {
    }

    public static final String STATEMENT_DATE_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter STATEMENT_DATE_FORMATTER = DateTimeFormatter.ofPattern(STATEMENT_DATE_FORMAT);

}
