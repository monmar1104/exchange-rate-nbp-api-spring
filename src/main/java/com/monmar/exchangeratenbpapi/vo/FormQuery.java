package com.monmar.exchangeratenbpapi.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormQuery {
    @Pattern(regexp="[a-zA-Z]{3}", message="invalid format")
    private String currencyCode;
    private String dateFrom;
    private String dateTo;
}
