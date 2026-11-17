package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for retrieving low stock inventory alerts with pagination")
public class LowStockRequest extends PaginationRequestDto {

    @Min(value = 0, message = ErrorMessage.Analytics.ERR_THRESHOLD_MIN)
    @Max(value = 1000, message = ErrorMessage.Analytics.ERR_THRESHOLD_MAX)
    @Schema(description = "Inventory stock threshold to trigger low stock alert (0-1000)", example = "10", defaultValue = "10")
    private int threshold = 5;

    @Override
    public int getPageSize() {
        int size = super.getPageSize();
        return Math.min(size, 100);
    }
}
