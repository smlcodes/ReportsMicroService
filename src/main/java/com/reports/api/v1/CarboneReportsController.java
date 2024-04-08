package com.reports.api.v1;

import com.reports.ApplicationConstants;
import com.reports.api.v1.model.dto.ReportDataDto;
import com.reports.exception.BusinessException;
import com.reports.service.CarboneReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author Satya Kaveti
 */

@RestController
@RequestMapping(path = ApplicationConstants.API_BASE + ApplicationConstants.V1 + "carbone")
@Validated
@Slf4j
@RequiredArgsConstructor
public class CarboneReportsController {

    @Autowired
    private final CarboneReportService carboneReportService;

    @PostMapping("/employee")
    public ResponseEntity<Resource> employeeCarboneReport(@RequestBody ReportDataDto reportDataDto) {
        byte[] bytes = carboneReportService.employeeCarboneReport(reportDataDto);
        if (null != bytes) {
            ByteArrayResource resource = new ByteArrayResource(bytes);
            String fileName = "Employee_Carbone_Report" + "_" + LocalDate.now() + ".pdf";
            return ResponseEntity.ok()
                    .header(com.google.common.net.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            throw new BusinessException("File Download Failed");
        }
    }

}