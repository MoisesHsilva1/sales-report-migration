package com.example.sales.report.converter;

import com.example.sales.report.dto.ChannelDTO;
import com.example.sales.report.dto.SalesReportDTO;
import com.example.sales.report.dto.TransactionDTO;
import com.example.sales.report.model.ChannelModel;
import com.example.sales.report.model.SalesReportModel;
import com.example.sales.report.model.TransactionModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalesReportConverter {

    public SalesReportDTO toDto(SalesReportModel model) {
        if (model == null) {
            return null;
        }

        return new SalesReportDTO(
                model.getName(),
                toChannelDtoList(model.getChannels())
        );
    }

    public SalesReportModel toModel(SalesReportDTO dto) {
        if (dto == null) {
            return null;
        }

        return new SalesReportModel(
                dto.name(),
                toChannelModelList(dto.channels())
        );
    }

    private List<ChannelDTO> toChannelDtoList(List<ChannelModel> channels) {
        if (channels == null) {
            return List.of();
        }

        return channels.stream()
                .map(this::toDto)
                .toList();
    }

    private ChannelDTO toDto(ChannelModel model) {
        return new ChannelDTO(
                model.getName(),
                model.getTransactions() == null
                        ? List.of()
                        : model.getTransactions().stream()
                                .map(transaction -> new TransactionDTO(
                                        transaction.getCode(),
                                        transaction.getName()
                                ))
                                .toList()
        );
    }

    private List<ChannelModel> toChannelModelList(List<ChannelDTO> channels) {
        if (channels == null) {
            return List.of();
        }

        return channels.stream()
                .map(this::toModel)
                .toList();
    }

    private ChannelModel toModel(ChannelDTO dto) {
        List<TransactionModel> transactions = dto.transactions() == null
                ? List.of()
                : dto.transactions().stream()
                        .map(transaction -> new TransactionModel(
                                transaction.code(),
                                transaction.name()
                        ))
                        .toList();

        return new ChannelModel(dto.name(), transactions);
    }
}
