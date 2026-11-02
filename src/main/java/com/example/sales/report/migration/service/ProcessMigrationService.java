package com.example.sales.report.migration.service;

import com.example.sales.report.migration.branch.ReportMigrationBranch;
import com.example.sales.report.dto.SalesReportParams;
import com.example.sales.report.model.SalesReportModel;

import java.util.List;

public interface ProcessMigrationService {
    SalesReportModel insertReport(SalesReportParams params);
    void rollbackReport(String reportName);
    void insertBranch(List<ReportMigrationBranch> reportMigrationBranches);
}
