package br.com.liferay.daniel.pointrecord.domain;

import javax.persistence.*;

@Entity
@Table(name = "UNKNOWN_ERROR")
public class UnknownError {

    @Id
    private String uuid;

    @Lob
    @Column
    private String logger;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

}
