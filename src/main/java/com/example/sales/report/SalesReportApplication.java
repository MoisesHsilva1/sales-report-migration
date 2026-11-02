package com.example.sales.report;

import io.flamingock.api.annotations.EnableFlamingock;
import io.flamingock.api.annotations.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFlamingock(
		stages = {
				@Stage(location = "com.example.sales.report.migration")
		}
)
@SpringBootApplication
public class SalesReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesReportApplication.class, args);
    }

}
