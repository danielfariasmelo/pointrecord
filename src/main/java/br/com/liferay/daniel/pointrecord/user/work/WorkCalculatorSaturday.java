package br.com.liferay.daniel.pointrecord.user.work;

import org.springframework.stereotype.Component;

@Component ("WorkCalculatorSaturday")
public class WorkCalculatorSaturday extends WorkCalculatorAbstract {

    @Override
    public Double getFactorWork() {
        return 0D;
    }

    @Override
    public Double getFactorRest() {
        return 0D;
    }

}
