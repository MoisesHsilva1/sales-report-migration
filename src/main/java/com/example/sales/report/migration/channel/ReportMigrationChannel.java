package com.example.sales.report.migration.channel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportMigrationChannel {
    private String reportName;
    private String channelName;
}
