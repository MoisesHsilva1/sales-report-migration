package com.example.sales.report.migration;

public interface Migration {
    void apply();
    void rollback();
}
