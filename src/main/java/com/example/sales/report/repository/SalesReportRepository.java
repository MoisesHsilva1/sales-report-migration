package com.example.sales.report.repository;

import com.example.sales.report.model.SalesReportModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesReportRepository extends MongoRepository<SalesReportModel, String> {
    Optional<SalesReportModel> findReportByName(String reportName);
    void deleteByName(String reportName);
}

