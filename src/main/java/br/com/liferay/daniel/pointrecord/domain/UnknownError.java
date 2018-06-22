package br.com.liferay.daniel.pointrecord.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "UNKNOWN_ERROR")
public class UnknownError implements Serializable {

    @Id
    private String uuid;

    @Lob
    @Column
    private String logger;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHour;

    @Lob
    @Type(type="org.hibernate.type.TextType")
    @Column
    private String cause;
    
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

    public Date getDateHour() {
        return dateHour;
    }

    public void setDateHour(Date dateHour) {
        this.dateHour = dateHour;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
