package com.reports.service.impl;

import com.reports.api.v1.model.dto.CarbonReportDto;
import com.reports.api.v1.model.dto.EmployeeDto;
import com.reports.api.v1.model.dto.ReportDataDto;
import com.reports.service.CarboneReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;


/**
 * @author Satya Kaveti
 */


@Service
@Slf4j
@AllArgsConstructor
public class CarboneReportsServiceImpl implements CarboneReportService {

    private static final String reportPath = "download";

    private static final String templateID = "5d7077481690ec0c0c63cfc04472ccc9c2afd635ae66500eb0313571ec2ff022";
    private static final String carboneToken = "test_eyJhbGciOiJFUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI1MzE1MjUwOTE1NjI2ODY1ODUiLCJhdWQiOiJjYXJib25lIiwiZXhwIjoyMzM1MDc3OTI5LCJkYXRhIjp7ImlkQWNjb3VudCI6IjUzMTUyNTA5MTU2MjY4NjU4NSJ9fQ.AN7CtEIaJA5FUR2qkcsAs7YvABs1OMxSqC56OgdJWsz4TfYlpwm5Ow0bMz1g8DW6PX0QDLXd3onmJfg_PLQ2LU9YAV5y3bH-8fgGU-3n1jq6mWB8wm2Fp7H3ajiidfqXxZnZzw7FhqRAYlMboTArqx0S6x8-myUU5uceQfOg8PRaa4Bv";


    private final ObjectMapper objectMapper;



    @Override
    public byte[] employeeCarboneReport(ReportDataDto reportDataDto) {

        try {
            List<EmployeeDto> employeeDtos = reportDataDto.getData();
            CarbonReportDto dto = CarbonReportDto.builder().employeeList(employeeDtos).convertTo("pdf").build();
            String renderId = getCarboneRenderId(dto);
            return downloadCarboneReport(renderId);

        } catch (JsonProcessingException e) {
            log.error("Error while converting JSON", e);
        } catch (Exception e) {
            log.error("Error while employeeCarboneReport", e);
        }
        return null;
    }


    private String getCarboneRenderId(CarbonReportDto dto) throws Exception{
        String renderId = null;
        String url = "https://api.carbone.io/render/" + templateID;
        HttpHeaders headers = new HttpHeaders();
        headers.set("carbone-version", "4");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", carboneToken);

        // Convert dto to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestBody = objectMapper.writeValueAsString(dto);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("Response: " + response.getBody());

        if(!ObjectUtils.isEmpty(response)){
            String responseBody = response.getBody();
            JSONObject jsonResponse = new JSONObject(responseBody);
            renderId = jsonResponse.getJSONObject("data").getString("renderId");
            log.info("Render Id "+renderId);
        }
        return renderId;
    }


    public byte[]  downloadCarboneReport(String renderId) throws IOException {
        String url = "https://api.carbone.io/render/"+renderId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("carbone-version", "4");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        return response.getBody();
    }

}


