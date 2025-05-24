package com.example.imagepath_analysys.api.mock.model;

import com.example.imagepath_analysys.api.mock.enums.ApiErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AiAnalysisModel {

    private Boolean success;
    private String message;
    @JsonProperty("estimated_data")
    private EstimatedData estimatedData;

    /**
     * success
     */
    public AiAnalysisModel(EstimatedData estimatedData) {
        this.success = true;
        this.message = "success";
        this.estimatedData = estimatedData;
    }

    /**
     * error
     * @param errorCode {{@link ApiErrorCode}}
     */
    public AiAnalysisModel(ApiErrorCode errorCode) {
        this.success = false;
        this.message = "Error:" + errorCode.name();
    }
}
