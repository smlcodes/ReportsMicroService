package com.reports.api.v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author satyakaveti on 02/04/24
 */
@Data
@Builder
public class CarbonReportDto {

        @JsonProperty("data")
        private List<EmployeeDto> employeeList;

        @JsonProperty("convertTo")
        private String convertTo;

    }
