package com.txt1stparkuor.Ecommerce.base;

import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping(UrlConstant.BASE_URL)
public @interface RestApiV1 {
}
