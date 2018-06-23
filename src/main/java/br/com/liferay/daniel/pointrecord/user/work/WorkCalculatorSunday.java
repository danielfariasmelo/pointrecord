package br.com.liferay.daniel.pointrecord.user.work;

import org.springframework.stereotype.Component;

@Component ("WorkCalculatorSunday")
public class WorkCalculatorSunday extends WorkCalculatorAbstract {

    @Override
    public Double getFactorWork() {
        return 0D;
    }

}
