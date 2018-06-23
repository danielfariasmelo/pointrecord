package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.UserWork;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class WorkCalculatorAbstract implements WorkCalculator {

    @Override
    public UserWork calculate(List<LocalDateTime> registers) {
        final UserWork userWork = new UserWork();
        LocalDateTime registerInitial = null;
        LocalDateTime registerFinal = null;

        for (int i = 0; i<registers.size(); i++){

            if(i % 2 ==0){
                registerInitial = registers.get(i);

            }else{
                registerFinal = registers.get(i);
            }

            if(registerFinal!=null){
                userWork.setDate(registers.get(i).toLocalDate());

                if(registerFinal.getHour()>= 22 || (registerInitial.getHour() >= 0 && registerInitial.getHour()<=6)) {
                    final Duration duration = Duration.between(registerInitial,registerFinal);
                    userWork.setWork(userWork.getWork() + (duration.toMinutes() * 1.2));
                }else{
                    final Duration duration = Duration.between(registerInitial,registerFinal);
                    userWork.setWork(userWork.getWork() + duration.toMinutes());
                }
                registerFinal = null;
            }

        }

        return userWork;
    }


    @Override
    public Double getFactorWork() {
        return 1.0;
    }

    @Override
    public Double getFactorRest() {
        return 1.0;
    }
}
