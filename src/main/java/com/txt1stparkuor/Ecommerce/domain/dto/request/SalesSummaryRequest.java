package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for retrieving sales summary metrics with date restriction (max 1 year)")
public class SalesSummaryRequest {

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Start date (YYYY-MM-DD)", example = "2026-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate from;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "End date (YYYY-MM-DD)", example = "2026-10-24", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate to;
}
