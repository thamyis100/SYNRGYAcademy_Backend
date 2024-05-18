package synrgy7thapmoch4.Controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synrgy7thapmoch4.Entity.Order_Detail;
import synrgy7thapmoch4.Services.ReportService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping("/invoice")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody Map<String, String> requestBody) {
        String merchantName = requestBody.get("merchantName");
        try {
            logger.info("Generating invoice for merchant: '{}'", merchantName);
            List<Order_Detail> orderDetails = reportService.getOrderDetailsByMerchantName(merchantName);
            String templatePath = (orderDetails != null && !orderDetails.isEmpty()) ? "/invoice.jrxml" : "/no_data.jrxml";
            logger.info(templatePath);

            // Load the Jasper template
            InputStream jasperStream = this.getClass().getResourceAsStream(templatePath);
            if (jasperStream == null) {
                logger.warn("Failed to load Jasper template: '/invoice.jrxml'");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
            logger.info("Jasper template loaded successfully.");

            // Create data source
            JRRewindableDataSource dataSource = (orderDetails == null || orderDetails.isEmpty())
                    ? new JREmptyDataSource()
                    : new JRBeanCollectionDataSource(orderDetails);
            logger.info("Data source created successfully with {} records.", orderDetails.size());

            // Create parameters map
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("merchant", merchantName);
            logger.info("Parameters map created successfully with merchant name: '{}'", merchantName);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            logger.info("Report filled successfully with {} records.", orderDetails.size());

            // Export the report to PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename("invoice.pdf").build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (JRException e) {
            logger.warn("An error occurred while generating the invoice: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
