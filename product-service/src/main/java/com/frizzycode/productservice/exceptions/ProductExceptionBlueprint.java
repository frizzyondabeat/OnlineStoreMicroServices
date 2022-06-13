package com.frizzycode.productservice.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@Builder
public class ProductExceptionBlueprint {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime time;
}
