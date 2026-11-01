package com.example.sales.report.dto;

import java.util.List;

public record ChannelDTO(
        String name,
        List<TransactionDTO> transactions
) {
}
