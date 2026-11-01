package com.example.sales.report.migration.service.impl;

import com.example.sales.report.migration.service.ProcessMigrationService;
import com.example.sales.report.migration.channel.ReportMigrationChannel;
import com.example.sales.report.model.ChannelModel;
import com.example.sales.report.model.SalesReportModel;
import com.example.sales.report.repository.SalesReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProcessMigrationServiceImpl implements ProcessMigrationService {

    private final SalesReportRepository salesReportRepository;

    @Override
    public void createReport(String reportName) {
        if (salesReportRepository.findReportByName(reportName).isEmpty()) {
            salesReportRepository.save(new SalesReportModel(reportName, new ArrayList<>()));
        }
    }

    @Override
    public void insertChannel(List<ReportMigrationChannel> reportMigrationChannelsList) {
        reportMigrationChannelsList.forEach(reportMigrationChannel ->
                insertChannel(
                        reportMigrationChannel.getReportName(),
                        reportMigrationChannel.getChannelName()
                )
        );
    }

    private void insertChannel(String reportName, String channelName) {
        SalesReportModel report = salesReportRepository.findReportByName(reportName)
                .orElseThrow(() -> new IllegalArgumentException("Report not found" + reportName));

        List<ChannelModel> channels = report.getChannels();

        if (channels == null) {
            channels = new ArrayList<>();
            report.setChannels(channels);
        }

        boolean channelAlreadyExists = channels.stream()
                .anyMatch(channel -> channel.getName().equalsIgnoreCase(channelName));

        if (channelAlreadyExists) {
            throw new IllegalStateException("Channel already exists: " + channelName);
        }

        ChannelModel channel = new ChannelModel(
                channelName,
                new ArrayList<>()
        );

        channels.add(channel);

        salesReportRepository.save(report);
    }

}

