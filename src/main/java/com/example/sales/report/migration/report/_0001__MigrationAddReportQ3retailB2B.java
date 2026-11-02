package com.example.sales.report.migration.report;

import com.example.sales.report.Util;
import com.example.sales.report.dto.SalesReportParams;
import com.example.sales.report.migration.service.impl.ProcessMigrationServiceImpl;
import io.flamingock.api.annotations.Apply;
import io.flamingock.api.annotations.Change;
import io.flamingock.api.annotations.Rollback;
import io.flamingock.api.annotations.TargetSystem;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
@TargetSystem(id = "mongodb")
@Change(id = "add-report-q3-retail-b2b-20261101", author = "Moises Henrique", transactional = false)
public class _0001__MigrationAddReportQ3retailB2B {
    private static final String REPORT_RESOURCE_PATH = "migration/report/add-report-q3-retail-b2b-20261101.json";
    private static final String REPORT_NAME = "REPORT_Q3_RETAIL_B2B";

    @Apply
    public void apply(Util util, ProcessMigrationServiceImpl processMigrationService) {
        processMigrationService.insertReport(buildReport(util));
    }

    @Rollback
    public void rollback(ProcessMigrationServiceImpl processMigrationService) {
        processMigrationService.rollbackReport(REPORT_NAME);
    }

    private SalesReportParams buildReport(Util util) {
        return util.parseJsonToType(loadReportJson(), SalesReportParams.class);
    }

    private String loadReportJson() {
        return loadReportJson(REPORT_RESOURCE_PATH);
    }

    String loadReportJson(String resourcePath) {
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);

        try {
            return StreamUtils.copyToString(classPathResource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new IllegalStateException("Error while loading report JSON" + resourcePath, ex);
        }
    }
}
