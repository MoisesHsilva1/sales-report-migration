package com.example.sales.report.migration.branch;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportMigrationBranch {
    private String reportName;
    private String branchName;
}
