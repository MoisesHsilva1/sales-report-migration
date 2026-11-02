package com.example.sales.report.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodModel {
    private Integer year;

    private Integer quarter;

    private LocalDate startDate;

    private LocalDate endDate;
}
