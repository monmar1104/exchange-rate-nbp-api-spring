package com.monmar.exchangeratenbpapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monmar.exchangeratenbpapi.dto.ExchangeRateResponse;
import com.monmar.exchangeratenbpapi.dto.Rate;
import com.monmar.exchangeratenbpapi.exception.ExchangeRateBadRequestException;
import com.monmar.exchangeratenbpapi.exception.ExchangeRateNotFoundException;
import com.monmar.exchangeratenbpapi.vo.FormQuery;
import com.monmar.exchangeratenbpapi.vo.StandardDeviation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final String API_URL = "https://api.nbp.pl/api/exchangerates/rates/C/";

    @Override
    public ExchangeRateResponse getExchangeRateResponseByCodeByDate(FormQuery formQuery) {
        ExchangeRateResponse exchangeRateResponse = null;
        Client client = Client.create();
        WebResource resource = client.resource(API_URL + formQuery.getCurrencyCode() + "/" + formQuery.getDateFrom() + "/" + formQuery.getDateTo());
        ClientResponse response = resource.accept("application/json").get(ClientResponse.class);

        if (response.getStatus() == 404) {
            throw new ExchangeRateNotFoundException("No Exchange rate for given parameters.");
        } else if (response.getStatus() == 400) {
            throw new ExchangeRateBadRequestException("Bad request");
        } else {

            String jsonBody = response.getEntity(String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                exchangeRateResponse = objectMapper.readValue(jsonBody, ExchangeRateResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return exchangeRateResponse;
        }
    }

    public double getBidPriceAverageFromList(List<Rate> rates) {
        return rates.stream().mapToDouble(Rate::getBid).average().orElse(0.0);
    }


    public double getAskPriceStandardDeviationFromList(List<Rate> rates) {
        return StandardDeviation.getStandardDeviationOfPopulFromList(rates);
    }
}
