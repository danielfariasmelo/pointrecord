package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;

import java.util.List;

public interface WorkCalculator {

    UserWorkDTO calculate(List<Point> registers);

    Double getFactorWork();

    Double getFactorRest();

}
