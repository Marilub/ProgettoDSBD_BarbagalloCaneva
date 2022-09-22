package com.example.doctorservice.Events;

import org.springframework.context.ApplicationEvent;

public class EventoRicovero extends ApplicationEvent {
    private Boolean successoRicovero;

    public EventoRicovero(Object source,Boolean successoRicovero){
        super(source);
        this.successoRicovero=successoRicovero;
    }

    public Boolean getSuccessoRicovero() {
        return successoRicovero;
    }
}
