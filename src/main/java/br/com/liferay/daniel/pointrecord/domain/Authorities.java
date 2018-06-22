package br.com.liferay.daniel.pointrecord.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "AUTHORITIES")
public class Authorities implements Serializable {

    @EmbeddedId
    private AuthoritiesPK authoritiesPK;

    public AuthoritiesPK getAuthoritiesPK() {
        return authoritiesPK;
    }

    public void setAuthoritiesPK(AuthoritiesPK authoritiesPK) {
        this.authoritiesPK = authoritiesPK;
    }
}
