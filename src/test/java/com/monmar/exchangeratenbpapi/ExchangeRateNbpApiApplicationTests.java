package com.monmar.exchangeratenbpapi;


import com.monmar.exchangeratenbpapi.dto.Rate;
import com.monmar.exchangeratenbpapi.exception.ExchangeRateNotFoundException;
import com.monmar.exchangeratenbpapi.service.ExchangeRateService;

import com.monmar.exchangeratenbpapi.service.ExchangeRateServiceImpl;
import com.monmar.exchangeratenbpapi.vo.FormQuery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExchangeRateNbpApiApplicationTests {

    private ExchangeRateService exchangeRateService;

    @Before
    public void doBefore() {
        exchangeRateService = new ExchangeRateServiceImpl();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnFiveRatesForEuro() {
        final String currencyCode = "EUR";
        final String dateFrom = "2017-11-20";
        final String dateTo = "2017-11-24";

        assertEquals(5, exchangeRateService.getExchangeRateResponseByCodeByDate(new FormQuery(currencyCode, dateFrom, dateTo)).getRates().size());

    }

    @Test
    public void shouldReturnAskPriceStandardDeviation() {
        final String currencyCode = "EUR";
        final String dateFrom = "2017-11-20";
        final String dateTo = "2017-11-24";
        List<Rate> rates = exchangeRateService.getExchangeRateResponseByCodeByDate(new FormQuery(currencyCode, dateFrom, dateTo)).getRates();

        assertEquals(0.0101, exchangeRateService.getAskPriceStandardDeviationFromList(rates), 0.0001);
    }

    @Test
    public void shouldReturnBidPriceAverage() {
        final String currencyCode = "EUR";
        final String dateFrom = "2017-11-20";
        final String dateTo = "2017-11-24";
        List<Rate> rates = exchangeRateService.getExchangeRateResponseByCodeByDate(new FormQuery(currencyCode, dateFrom, dateTo)).getRates();

        assertEquals(4.1815, exchangeRateService.getBidPriceAverageFromList(rates), 0.0001);
    }

    @Test
    public void shouldHandleFailure() {
        expectedException.expect(ExchangeRateNotFoundException.class);
        expectedException.expectMessage("No Exchange rate for given parameters.");

        final String notExistingCurrencyCode = "TMP";
        final String dateFrom = "2017-11-20";
        final String dateTo = "2017-11-24";
        List<Rate> rates = exchangeRateService.getExchangeRateResponseByCodeByDate(new FormQuery(notExistingCurrencyCode, dateFrom, dateTo)).getRates();
        exchangeRateService.getBidPriceAverageFromList(rates);
    }
}
