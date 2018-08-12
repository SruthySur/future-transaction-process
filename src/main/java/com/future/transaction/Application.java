package com.future.transaction;

import com.future.transaction.summary.ReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportGenerator summaryGenerator;


    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Override
    public void run(String... args) {

        log.info("Start processing");

        summaryGenerator.generate();

        log.info("Completed processing");
    }

}
