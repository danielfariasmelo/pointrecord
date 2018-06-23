package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.UserWork;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkCalculator {

    UserWork calculate(List<LocalDateTime> registers);

    Double getFactorWork();


}
