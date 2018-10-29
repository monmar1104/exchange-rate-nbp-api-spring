package com.monmar.exchangeratenbpapi.service;

import com.monmar.exchangeratenbpapi.dto.ExchangeRateResponse;
import com.monmar.exchangeratenbpapi.dto.Rate;
import com.monmar.exchangeratenbpapi.vo.FormQuery;

import java.util.List;

public interface ExchangeRateService {

    ExchangeRateResponse getExchangeRateResponseByCodeByDate(FormQuery formQuery);

    double getBidPriceAverageFromList(List<Rate> rates);

    double getAskPriceStandardDeviationFromList(List<Rate> rates);

}
