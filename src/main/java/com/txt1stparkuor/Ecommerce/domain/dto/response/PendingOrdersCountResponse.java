package com.txt1stparkuor.Ecommerce.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Pending orders count response for analytics dashboard")
public class PendingOrdersCountResponse {

    @Schema(description = "Count of orders currently in PENDING status", example = "15")
    private long pendingOrdersCount;
}
