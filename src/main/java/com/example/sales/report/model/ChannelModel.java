package com.example.sales.report.model;

import com.example.sales.report.model.enums.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelModel {
    private ChannelType channelType;

    private List<PeriodResultModel> periodResults = new ArrayList<>();
}
