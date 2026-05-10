package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationFullRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for filtering users with pagination")
public class UserFilterRequest extends PaginationFullRequestDto {
    @Schema(description = "Role of the user", example = "USER")
    private String role;
}
