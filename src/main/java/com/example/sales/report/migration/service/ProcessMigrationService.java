package com.example.sales.report.migration.service;

import com.example.sales.report.migration.channel.ReportMigrationChannel;

import java.util.List;

public interface ProcessMigrationService {
    void createReport(String reportName);
    void insertChannel(List<ReportMigrationChannel> reportMigrationChannelsList);
}
