package br.com.liferay.daniel.pointrecord.domain;

import java.time.LocalDate;

public class UserWork {

    private LocalDate date;

    private Double work = 0D;

    private Double rest = 0D;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getWork() {
        return work;
    }

    public void setWork(Double work) {
        this.work = work;
    }

    public Double getRest() {
        return rest;
    }

    public void setRest(Double rest) {
        this.rest = rest;
    }
}
