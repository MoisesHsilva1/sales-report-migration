package com.example.sales.report.dto;

import com.example.sales.report.model.BranchModel;
import com.example.sales.report.model.CriteriaModel;
import com.example.sales.report.model.PeriodModel;
import com.example.sales.report.model.enums.ReportGranularity;
import com.example.sales.report.model.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReportParams {

    private String name;

    private ReportStatus status;

    private ReportGranularity granularity;

    @Builder.Default
    private List<BranchModel> branches = new ArrayList<>();

    private PeriodModel period;

    private CriteriaModel criteria;
}
