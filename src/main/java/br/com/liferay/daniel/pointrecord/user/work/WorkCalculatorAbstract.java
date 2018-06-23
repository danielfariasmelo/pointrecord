package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.UserWork;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class WorkCalculatorAbstract implements WorkCalculator {

    @Override
    public UserWork calculate(final List<LocalDateTime> registers) {
        final UserWork userWork = new UserWork();
        userWork.setDate(registers.get(0).toLocalDate());
        LocalDateTime registerInitial = null;
        LocalDateTime registerFinal = null;

        for (int i = 0; i<registers.size(); i++){

            if(i % 2 ==0){

                if(registerFinal != null){
                    final Duration durationRest = Duration.between(registerFinal,registers.get(i));
                    userWork.setRest(userWork.getRest() + durationRest.toMinutes());
                    registerFinal = null;
                }

                registerInitial = registers.get(i);

            }else{
                registerFinal = registers.get(i);
            }

            if(registerFinal!=null){

                if(registerFinal.getHour()>= 22 || (registerInitial.getHour() >= 0 && registerInitial.getHour()<=6)) {

                    final Duration duration = Duration.between(registerInitial,registerFinal);
                    userWork.setWork(userWork.getWork() + (duration.toMinutes() * 1.2));

                }else{

                    final Duration duration = Duration.between(registerInitial,registerFinal);
                    userWork.setWork(userWork.getWork() + duration.toMinutes());

                }
            }
        }

        userWork.setRestRequired(calculateRequiredRest(userWork.getWork(),userWork.getRest()));

        return userWork;
    }

    public Double calculateRequiredRest(final Double work , final Double rest){
        Double restRequired = 0D;

        if(work > 4 && work <= 6){
            restRequired = 15D;
        }else if( work > 6){
            restRequired = 60D;
        }

        final Double totalRestCalculate = restRequired - rest;

        return totalRestCalculate <0 ? 0D : totalRestCalculate;
    }


    @Override
    public Double getFactorWork() {
        return 1.0;
    }

}
