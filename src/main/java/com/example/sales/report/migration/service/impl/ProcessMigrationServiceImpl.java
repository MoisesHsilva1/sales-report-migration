package com.example.sales.report.migration.service.impl;

import com.example.sales.report.migration.service.ProcessMigrationService;
import com.example.sales.report.migration.branch.ReportMigrationBranch;
import com.example.sales.report.dto.SalesReportParams;
import com.example.sales.report.model.BranchModel;
import com.example.sales.report.model.SalesReportModel;
import com.example.sales.report.repository.SalesReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ProcessMigrationServiceImpl implements ProcessMigrationService {

    private final SalesReportRepository salesReportRepository;

    @Override
    public SalesReportModel insertReport(SalesReportParams params) {
        Objects.requireNonNull(params, "Report parameters must not be null");

        if (params.getName() == null || params.getName().isBlank()) {
            throw new IllegalArgumentException("Report name must not be blank");
        }

        return salesReportRepository.findReportByName(params.getName())
                .orElseGet(() -> saveReport(params));
    }

    @Override
    public void rollbackReport(String reportName) {
        if (reportName == null || reportName.isBlank()) {
            throw new IllegalArgumentException("Report name must not be blank");
        }

        salesReportRepository.deleteByName(reportName);
    }

    private SalesReportModel saveReport(SalesReportParams params) {
        SalesReportModel report = new SalesReportModel();
        report.setName(params.getName());
        report.setStatus(params.getStatus());
        report.setGranularity(params.getGranularity());
        report.setPeriod(params.getPeriod());
        report.setCriteria(params.getCriteria());
        report.setBranches(params.getBranches() == null
                ? new ArrayList<>()
                : new ArrayList<>(params.getBranches()));

        return salesReportRepository.save(report);
    }

    @Override
    public void insertBranch(List<ReportMigrationBranch> reportMigrationBranches) {
        reportMigrationBranches.forEach(reportMigrationBranch ->
                insertBranch(
                        reportMigrationBranch.getReportName(),
                        reportMigrationBranch.getBranchName()
                )
        );
    }

    private void insertBranch(String reportName, String branchName) {
        SalesReportModel report = salesReportRepository.findReportByName(reportName)
                .orElseThrow(() -> new IllegalArgumentException("Report not found" + reportName));

        List<BranchModel> branches = report.getBranches();

        if (branches == null) {
            branches = new ArrayList<>();
            report.setBranches(branches);
        }

        boolean branchAlreadyExists = branches.stream()
                .anyMatch(branch -> branch.getName().equalsIgnoreCase(branchName));

        if (branchAlreadyExists) {
            throw new IllegalStateException("Branch already exists: " + branchName);
        }

        BranchModel branch = new BranchModel(
                null,
                branchName,
                null,
                null,
                new ArrayList<>()
        );

        branches.add(branch);

        salesReportRepository.save(report);
    }

}

