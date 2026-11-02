package com.example.sales.report.migration.service.impl;

import com.example.sales.report.dto.SalesReportParams;
import com.example.sales.report.migration.branch.ReportMigrationBranch;
import com.example.sales.report.model.BranchModel;
import com.example.sales.report.model.SalesReportModel;
import com.example.sales.report.repository.SalesReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessMigrationServiceImplTests {

    @Mock
    private SalesReportRepository salesReportRepository;

    @InjectMocks
    private ProcessMigrationServiceImpl service;

    @Test
    void rejectsNullReportParameters() {
        assertThatThrownBy(() -> service.insertReport(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Report parameters must not be null");
    }

    @Test
    void rejectsBlankReportName() {
        SalesReportParams params = SalesReportParams.builder().name(" ").build();

        assertThatThrownBy(() -> service.insertReport(params))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Report name must not be blank");
    }

    @Test
    void returnsExistingReportWithoutSaving() {
        SalesReportModel existing = report("existing");
        when(salesReportRepository.findReportByName("existing")).thenReturn(Optional.of(existing));

        SalesReportModel result = service.insertReport(SalesReportParams.builder().name("existing").build());

        assertThat(result).isSameAs(existing);
        verify(salesReportRepository, never()).save(any());
    }

    @Test
    void rollsBackReportByName() {
        service.rollbackReport("report-to-remove");

        verify(salesReportRepository).deleteByName("report-to-remove");
    }

    @Test
    void rejectsBlankRollbackReportName() {
        assertThatThrownBy(() -> service.rollbackReport(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Report name must not be blank");
    }

    @Test
    void mapsAndSavesNewReportWithIndependentBranchList() {
        List<BranchModel> branches = new ArrayList<>(List.of(new BranchModel()));
        SalesReportParams params = SalesReportParams.builder()
                .name("new-report")
                .branches(branches)
                .build();
        when(salesReportRepository.findReportByName("new-report")).thenReturn(Optional.empty());
        when(salesReportRepository.save(any(SalesReportModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SalesReportModel result = service.insertReport(params);

        assertThat(result.getName()).isEqualTo("new-report");
        assertThat(result.getBranches()).containsExactlyElementsOf(branches);
        assertThat(result.getBranches()).isNotSameAs(branches);
        verify(salesReportRepository).save(result);
    }

    @Test
    void insertsBranchIntoExistingReport() {
        SalesReportModel report = report("report");
        when(salesReportRepository.findReportByName("report")).thenReturn(Optional.of(report));

        service.insertBranch(List.of(ReportMigrationBranch.builder()
                .reportName("report")
                .branchName("São Paulo")
                .build()));

        assertThat(report.getBranches()).singleElement().extracting(BranchModel::getName)
                .isEqualTo("São Paulo");
        verify(salesReportRepository).save(report);
    }

    @Test
    void rejectsBranchWhenReportDoesNotExist() {
        when(salesReportRepository.findReportByName("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.insertBranch(List.of(ReportMigrationBranch.builder()
                .reportName("missing")
                .branchName("São Paulo")
                .build())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Report not foundmissing");
    }

    @Test
    void rejectsDuplicateBranchIgnoringCase() {
        SalesReportModel report = report("report");
        report.setBranches(new ArrayList<>(List.of(new BranchModel(null, "São Paulo", null, null, new ArrayList<>()))));
        when(salesReportRepository.findReportByName("report")).thenReturn(Optional.of(report));

        assertThatThrownBy(() -> service.insertBranch(List.of(ReportMigrationBranch.builder()
                .reportName("report")
                .branchName("SÃO PAULO")
                .build())))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Branch already exists: SÃO PAULO");
        verify(salesReportRepository, never()).save(any());
    }

    private SalesReportModel report(String name) {
        SalesReportModel report = new SalesReportModel();
        report.setName(name);
        report.setBranches(new ArrayList<>());
        return report;
    }
}
