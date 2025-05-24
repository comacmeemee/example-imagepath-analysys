package com.example.imagepath_analysys.api.mock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EstimatedData {

    @JsonProperty("class")
    private Integer clazz;
    private BigDecimal confidence;

    public EstimatedData(Integer clazz, BigDecimal confidence) {
        this.clazz = clazz;
        this.confidence = confidence;
    }
}
