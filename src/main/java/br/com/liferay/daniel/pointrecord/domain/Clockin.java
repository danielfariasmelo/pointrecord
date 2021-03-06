package br.com.liferay.daniel.pointrecord.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Clockin implements Serializable {

    private String pis;

    private LocalDateTime dateTime;

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
