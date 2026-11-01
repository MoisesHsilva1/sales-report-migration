package com.example.sales.report.migration.report;

import com.example.sales.report.migration.Migration;
import com.example.sales.report.migration.service.ProcessMigrationService;
import io.flamingock.api.annotations.Change;
import io.flamingock.api.annotations.Rollback;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Change(id = "create-report-start-year-20261031", author = "Moises Henrique")
public class _0001__CreateReportStartYear implements Migration {

    private static final String REPORT_NAME = "REPORT_START_YEAR";

    private final ProcessMigrationService processMigrationService;

    @Override
    public void apply() {
        processMigrationService.createReport(REPORT_NAME);
    }

    @Rollback
    public void rollback() {
    }
}
