package com.example.sales.report.model;

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
public class BranchModel {

    private String code;

    private String name;

    private LocationModel location;

    private String currencyCode;

    private List<ChannelModel> channels = new ArrayList<>();
}
