package br.com.liferay.daniel.pointrecord.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserWork {

    private LocalDate date;

    private Double work = 0D;

    private Double rest = 0D;

    private Double restRequired = 0D;

    private List<LocalDateTime> pointList;

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

    public Double getRestRequired() {
        return restRequired;
    }

    public List<LocalDateTime> getPointList() {
        return pointList;
    }

    public void setPointList(List<LocalDateTime> pointList) {
        this.pointList = pointList;
    }

    public void setRestRequired(Double restRequired) {
        this.restRequired = restRequired;
    }
}
