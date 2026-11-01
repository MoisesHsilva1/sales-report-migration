package com.example.sales.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private List<ChannelModel> channels = new ArrayList<>();

    public SalesReportModel(String name, List<ChannelModel> channels) {
        this.name = name;
        this.channels = channels;
    }
}
