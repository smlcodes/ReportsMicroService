package com.reports.service;

import com.reports.api.v1.model.dto.ReportDataDto;

/**
 * @author Zawar Shahid
 */

public interface JasperReportService {

    String employeeJasperReport(ReportDataDto fileType);
}