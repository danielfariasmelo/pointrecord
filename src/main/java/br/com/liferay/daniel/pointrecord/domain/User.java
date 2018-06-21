package br.com.liferay.daniel.pointrecord.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

    @Id
    private Long pis;

    @Column
    private String name;

    public Long getPis() {
        return pis;
    }

    public void setPis(Long pis) {
        this.pis = pis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
