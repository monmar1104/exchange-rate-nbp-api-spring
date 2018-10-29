package com.monmar.exchangeratenbpapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rate {

    private String no;
    private String effectiveDate;
    private double bid;
    private double ask;
}
