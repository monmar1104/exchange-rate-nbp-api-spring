package com.monmar.exchangeratenbpapi.controller;

import com.monmar.exchangeratenbpapi.dto.ExchangeRateResponse;
import com.monmar.exchangeratenbpapi.service.ExchangeRateService;
import com.monmar.exchangeratenbpapi.vo.FormQuery;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ExchangeRateController {

    @Autowired
    ExchangeRateService exchangeRateService;


    @GetMapping("/")
    public String showExchangeRateForm(Model model) {
        FormQuery formQuery = new FormQuery();

        model.addAttribute("resultAttr", false);
        model.addAttribute("formQuery", formQuery);

        return "show-exchange-rate-form";
    }

    @PostMapping("/showResultFromApi")
    public String showRatesByCodeByDate(@Valid @ModelAttribute("formQuery") FormQuery formQuery, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "show-exchange-rate-form";
        } else {


            ExchangeRateResponse exchangeRateResponse;

            exchangeRateResponse = exchangeRateService.getExchangeRateResponseByCodeByDate(formQuery);

            double standardDeviation = exchangeRateService.getAskPriceStandardDeviationFromList(exchangeRateResponse.getRates());
            double averageRate = exchangeRateService.getBidPriceAverageFromList(exchangeRateResponse.getRates());

            model.addAttribute("formQuery", formQuery);
            model.addAttribute("standardDeviation", String.format("%.4f", standardDeviation));
            model.addAttribute("averageRate", String.format("%.4f", averageRate));
            model.addAttribute("resultAttr", true);
            model.addAttribute("rateList", exchangeRateResponse);

            return "show-exchange-rate-form";
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
