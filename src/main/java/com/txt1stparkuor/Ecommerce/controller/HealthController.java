package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.annotation.ApiCommonResponses;
import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RestApiV1
@Tag(name = "Health Check", description = "Operations for checking application health status")
@ApiCommonResponses
public class HealthController {

    @Operation(summary = "Health check endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application is healthy and running")
    })
    @GetMapping(UrlConstant.Health.HEALTH_COMMON)
    public ResponseEntity<?> checkHealth() {
        return VsResponseUtil.success(HttpStatus.OK, Map.of("status", "UP"));
    }
}
