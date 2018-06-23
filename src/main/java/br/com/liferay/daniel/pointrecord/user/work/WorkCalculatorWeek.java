package br.com.liferay.daniel.pointrecord.user.work;

import org.springframework.stereotype.Component;

@Component ("WorkCalculatorWeek")
public class WorkCalculatorWeek extends WorkCalculatorAbstract {

    @Override
    public Double getFactorWork() {
        return null;
    }

    @Override
    public Double getFactorRest() {
        return null;
    }

}
