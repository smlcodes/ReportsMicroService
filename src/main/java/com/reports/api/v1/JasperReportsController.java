package com.reports.api.v1;

import com.reports.ApplicationConstants;
import com.reports.api.v1.model.dto.ReportDataDto;
import com.reports.exception.BusinessException;
import com.reports.service.JasperReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author Satya Kaveti
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "jasper")
@Validated
@Slf4j
@RequiredArgsConstructor
public class JasperReportsController {

    @Autowired
    private final JasperReportService jasperReportService;


    @PostMapping("/employee")
    public ResponseEntity<Resource> employeeJasperReport(@RequestBody ReportDataDto reportDataDto) {
        try {
            String downloadFilePath = jasperReportService.employeeJasperReport(reportDataDto);
            Resource resource = new FileSystemResource(downloadFilePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", downloadFilePath);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("Downloading Employees JSON Failed, Due to : " + e.getMessage());
        }
    }


}