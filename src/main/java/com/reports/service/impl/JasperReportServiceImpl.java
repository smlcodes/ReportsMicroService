package com.reports.service.impl;

import com.reports.api.v1.model.dto.EmployeeDto;
import com.reports.api.v1.model.dto.ReportDataDto;
import com.reports.exception.BusinessException;
import com.reports.service.JasperReportService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class JasperReportServiceImpl implements JasperReportService {

    private static final String reportPath = "download";
    @Autowired
    private ApplicationContext context;
    @Autowired
    private DataSource dataSource;

    @Override
    public String employeeJasperReport(ReportDataDto reportDataDto) {
        String template = "reports/emp.jrxml";

        List<EmployeeDto> employeeDtos = reportDataDto.getData();
        return generateJasperReport(template, employeeDtos, reportDataDto.getReportType());
    }

    public String generateJasperReport(String template, List dataSource, String fileType) {
        try {
            File file = ResourceUtils.getFile("classpath:" + template);
            InputStream input = new FileInputStream(file);

            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource);
            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Satya Kaveti");
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            String fileName = "Employee_report_" + System.currentTimeMillis();
            return exportJasperReport(jasperPrint, fileName, fileType);
        } catch (Exception e) {
            log.error("Error Occurred", e);
            throw new BusinessException("Error generateJasperReport");
        }
    }


    private String exportJasperReport(JasperPrint jasperPrint, String fileName, String fileType) throws Exception {
        String outputPath = "";
        switch (fileType) {
            case "csv":
                outputPath = reportPath + "//" + fileName + ".csv";
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputPath));
                SimpleCsvExporterConfiguration csvConfiguration = new SimpleCsvExporterConfiguration();
                csvConfiguration.setFieldDelimiter(",");
                csvExporter.setConfiguration(csvConfiguration);
                csvExporter.exportReport();
                break;
            case "xlsx":
                outputPath = reportPath + "//" + fileName + ".xlsx";
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
                SimpleXlsxReportConfiguration xlsxConfiguration = new SimpleXlsxReportConfiguration();
                xlsxConfiguration.setOnePagePerSheet(true);
                xlsxConfiguration.setRemoveEmptySpaceBetweenColumns(true);
                xlsxConfiguration.setDetectCellType(true);
                xlsxExporter.setConfiguration(xlsxConfiguration);
                xlsxExporter.exportReport();
                break;
            case "html":
                outputPath = reportPath + "//" + fileName + ".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, outputPath);
                break;
            case "xml":
                outputPath = reportPath + "//" + fileName + ".xml";
                JasperExportManager.exportReportToXmlFile(jasperPrint, outputPath, true);
                break;
            default:
                outputPath = reportPath + "//" + fileName + ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        }

        log.info(fileType.toUpperCase() + " File Generated at " + outputPath);
        return outputPath;
    }
}
