package br.com.liferay.daniel.pointrecord.domain;

import java.io.Serializable;
import java.util.List;

public class ResultDTO implements Serializable {

    private User user;

    private List<UserWork> userWorkList;

    private Double workPeriod;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserWork> getUserWorkList() {
        return userWorkList;
    }

    public void setUserWorkList(List<UserWork> userWorkList) {
        this.userWorkList = userWorkList;
    }

    public Double getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Double workPeriod) {
        this.workPeriod = workPeriod;
    }
}
