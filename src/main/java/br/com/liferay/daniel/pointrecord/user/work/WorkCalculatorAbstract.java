package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;

import java.time.LocalDateTime;
import java.util.List;

public class WorkCalculatorAbstract implements WorkCalculator {

    @Override
    public UserWorkDTO calculate(List<LocalDateTime> registers) {
        final UserWorkDTO userWorkDTO = new UserWorkDTO();

        registers.stream().forEach(register -> {

            this.getFactorWork();
            this.getFactorRest();

            userWorkDTO.setWork(userWorkDTO.getWork() + 0D);
            userWorkDTO.setRest(userWorkDTO.getRest() + 0D);

        });

        return userWorkDTO;
    }

    @Override
    public Double getFactorWork() {
        return 0D;
    }

    @Override
    public Double getFactorRest() {
        return 0D;
    }
}
