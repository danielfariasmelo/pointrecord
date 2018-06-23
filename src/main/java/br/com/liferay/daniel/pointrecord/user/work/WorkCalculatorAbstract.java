package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;

import java.util.List;

public class WorkCalculatorAbstract implements WorkCalculator {


    @Override
    public UserWorkDTO calculate(List<Point> registers) {
        this.getFactorWork();
        this.getFactorRest();

        final UserWorkDTO userWorkDTO = new UserWorkDTO();

        return userWorkDTO;
    }

    @Override
    public Double getFactorWork() {
        return null;
    }

    @Override
    public Double getFactorRest() {
        return null;
    }
}
