package com.txt1stparkuor.Ecommerce.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request DTO for filtering the category")
public class CategoryFilterRequest{
    @Schema(description = "Keyword to search for", example = "electronics")
    private String keyword;
}
