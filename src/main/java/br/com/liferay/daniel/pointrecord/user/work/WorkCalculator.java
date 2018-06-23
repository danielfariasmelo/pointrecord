package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkCalculator {

    UserWorkDTO calculate(List<LocalDateTime> registers);

    Double getFactorWork();

    Double getFactorRest();

}
