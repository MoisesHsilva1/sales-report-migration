package com.example.sales.report.migration.report;

import com.example.sales.report.Util;
import com.example.sales.report.dto.SalesReportParams;
import com.example.sales.report.migration.service.impl.ProcessMigrationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MigrationAddReportQ3retailB2BTests {

    private final Util util = mock(Util.class);
    private final ProcessMigrationServiceImpl processMigrationService = mock(ProcessMigrationServiceImpl.class);
    private final _0001__MigrationAddReportQ3retailB2B migration = new _0001__MigrationAddReportQ3retailB2B();

    @Test
    void loadsReportJsonFromClasspath() {
        String json = migration.loadReportJson("migration/report/add-report-q3-retail-b2b-20261101.json");

        assertThat(json).contains("REPORT_Q3_RETAIL_B2B");
    }

    @Test
    void failsWithHelpfulExceptionWhenResourceDoesNotExist() {
        assertThatThrownBy(() -> migration.loadReportJson("migration/report/missing.json"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("migration/report/missing.json");
    }

    @Test
    void parsesAndInsertsReportDuringApply() {
        SalesReportParams params = SalesReportParams.builder().name("Q3 Retail B2B").build();
        when(util.parseJsonToType(org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.eq(SalesReportParams.class))).thenReturn(params);

        migration.apply(util, processMigrationService);

        verify(util).parseJsonToType(org.mockito.ArgumentMatchers.contains("REPORT_Q3_RETAIL_B2B"),
                org.mockito.ArgumentMatchers.eq(SalesReportParams.class));
        verify(processMigrationService).insertReport(params);
    }

    @Test
    void rollsBackReportThroughMigrationService() {
        migration.rollback(processMigrationService);

        verify(processMigrationService).rollbackReport("REPORT_Q3_RETAIL_B2B");
    }
}
