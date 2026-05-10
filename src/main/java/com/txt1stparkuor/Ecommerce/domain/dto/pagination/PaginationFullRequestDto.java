package com.txt1stparkuor.Ecommerce.domain.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "Request DTO for full pagination, including sorting and keyword search")
public class PaginationFullRequestDto extends PaginationSortRequestDto{
    @Schema(description = "Keyword to search for", example = "electronics")
    private String keyword = "";
    public String getKeyword() {
        return keyword.trim();
    }
}