package com.txt1stparkuor.Ecommerce.domain.dto.pagination;

import com.txt1stparkuor.Ecommerce.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for basic pagination parameters")
public class PaginationRequestDto {

    @Schema(description = "Page number", example = "1")
    private Integer pageNum = 1;

    @Schema(description = "Number of items per page", example = "10")
    private Integer pageSize = 10;

    public int getPageNum() {
        if (this.pageNum == null || this.pageNum < 1) {
            return 0;
        }
        return this.pageNum - 1;
    }

    public int getPageSize() {
        if (this.pageSize == null || this.pageSize < 1) {
            return CommonConstant.PAGE_SIZE_DEFAULT;
        }
        return this.pageSize;
    }
}