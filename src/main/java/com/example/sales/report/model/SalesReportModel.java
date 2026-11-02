package com.example.sales.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.sales.report.model.enums.ReportGranularity;
import com.example.sales.report.model.enums.ReportStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "reports")
public class SalesReportModel {

    @Id
    @JsonIgnore
    private String id;

    private String name;

    private ReportStatus status;

    private ReportGranularity granularity;

    private PeriodModel period;

    private CriteriaModel criteria;

    private List<BranchModel> branches = new ArrayList<>();

    private Instant finalizedAt;
}
