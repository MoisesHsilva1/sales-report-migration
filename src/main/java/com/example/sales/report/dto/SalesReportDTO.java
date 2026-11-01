package com.example.sales.report.dto;

import java.util.List;

public record SalesReportDTO(
        String name,
        List<ChannelDTO> channels
) {
}
