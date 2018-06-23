package br.com.liferay.daniel.pointrecord.domain;

public class UserWorkDTO {

    private User user;

    private Double work;

    private Double rest;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
