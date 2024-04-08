package com.reports.service;

import com.reports.api.v1.model.dto.ReportDataDto;

public interface CarboneReportService {

    byte[] employeeCarboneReport(ReportDataDto fileType);
}
