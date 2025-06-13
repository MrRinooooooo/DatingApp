package com.app.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.services.AbbonamentoService;

@Component
public class AbbonamentoScheduler {

    @Autowired
    private AbbonamentoService abbonamentoService;

    // ogni giorno alle 0:00 di notte
    @Scheduled(cron = "0 0 0 * * *")
    public void controlloGiornalieroAbbonamenti() {
        abbonamentoService.controllaScadenzeAbbonamenti();
    }
}