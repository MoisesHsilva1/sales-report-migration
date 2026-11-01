package com.example.sales.report.migration.report.channel;

import com.example.sales.report.migration.Migration;
import com.example.sales.report.migration.channel.ReportMigrationChannel;
import com.example.sales.report.migration.service.ProcessMigrationService;
import com.example.sales.report.model.SalesReportModel;
import io.flamingock.api.annotations.Change;
import io.flamingock.api.annotations.Rollback;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Change(id = "add-channel-online-in-report-start-year-20261031", author = "Moises Henrique")
public class _0002__MigrationAddChannelOnlineReportStartYear implements Migration {

    private static final String REPORT_NAME = "REPORT_START_YEAR";
    private static final String CHANNEL_NAME = "ONLINE";

    private final ProcessMigrationService processMigrationService;

    private List<SalesReportModel> previousStates;

    @Override
    public void apply() {
        processMigrationService.insertChannel(buildMigrationConfig());
    }

    @Rollback
    public void rollback() {
    }

    private List<ReportMigrationChannel> buildMigrationConfig() {
        return List.of(ReportMigrationChannel.builder()
                .reportName(REPORT_NAME)
                .channelName(CHANNEL_NAME)
                .build());
    }
}
