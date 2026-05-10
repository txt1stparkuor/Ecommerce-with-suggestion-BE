package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.annotation.ApiCommonResponses;
import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.service.CsvExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "CSV Export", description = "Operations for exporting data to CSV format")
@ApiCommonResponses
public class CsvExportController {

    CsvExportService csvExportService;

    @Operation(
            summary = "Export Amazon CSV",
            description = "Exports product data in a format compatible with Amazon. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV file generated and returned successfully")
    })
    @GetMapping(UrlConstant.Export.EXPORT_AMAZON_CSV)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getFile() {
        String filename = "amazon.csv";
        InputStreamResource file = new InputStreamResource(csvExportService.generateAmazonCsv());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}