package com.example.sales.report.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryModel {

    private String code;

    private String name;

    private Integer quantity;

    private BigDecimal grossSalesAmount;
}
