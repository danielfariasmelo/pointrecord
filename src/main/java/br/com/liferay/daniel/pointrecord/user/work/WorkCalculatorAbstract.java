package br.com.liferay.daniel.pointrecord.user.work;

import br.com.liferay.daniel.pointrecord.domain.UserWork;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class WorkCalculatorAbstract implements WorkCalculator {

    private static Double WORK_MIN = 240D;
    private static Double WORK_MAX = 360D;

    private static Double REST_WORK_MIN = 15D;
    private static Double REST_WORK_MAX = 60D;

    /**
     * This is the main method for performing the calculation of a user's hours. Basically it works as follows:
     * This method receives a list of points on a given day, and calculates the differences between the times of the
     * incoming and outgoing beats. In addition it is calculated overtime as well as the necessary rest according to
     * the hours worked. This method return the object UserWork.
     *
     * @param registers
     * @return The UserWork
     */
    @Override
    public UserWork calculate(final List<LocalDateTime> registers) {
        final UserWork userWork = new UserWork();
        userWork.setDate(registers.get(0).toLocalDate());
        userWork.setPointList(registers);
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

                if(registerFinal.getHour() >= 22){

                    final Duration duration = Duration.between(registerInitial, registerFinal.withHour(22).withMinute(0).withSecond(0));
                    final Duration durationExtra = Duration.between(registerFinal.withHour(22).withMinute(0).withSecond(0),registerFinal);

                    userWork.setWork(userWork.getWork()
                            + (duration.toMinutes() * getFactorWork())
                            + (durationExtra.toMinutes() * 1.2 * getFactorWork()));

                }else if(registerInitial.getHour() >= 0 && registerInitial.getHour()<=6){

                    if(registerFinal.getHour()>6){
                        final Duration durationExtra = Duration.between(registerInitial,registerInitial.withHour(6).withMinute(0).withSecond(0));
                        final Duration duration = Duration.between(registerInitial.withHour(6).withMinute(0).withSecond(0),registerFinal);
                        userWork.setWork(userWork.getWork()
                                + (duration.toMinutes() * getFactorWork())
                                + (durationExtra.toMinutes() * 1.2 * getFactorWork()));

                    }else{
                        final Duration duration = Duration.between(registerInitial,registerFinal);
                        userWork.setWork(userWork.getWork()
                                + (duration.toMinutes() * 1.2 * getFactorWork()));
                    }

                }else{

                    final Duration duration = Duration.between(registerInitial,registerFinal);
                    userWork.setWork(userWork.getWork() + (duration.toMinutes() * getFactorWork()));

                }
            }
        }

        userWork.setRestRequired(calculateRequiredRest(userWork.getWork(),userWork.getRest()));

        return userWork;
    }

    /**
     * This is the method responsible for calculating the rest hours of a user, according to the hours worked.
     *
     * @param work
     * @param rest
     * @return The rest required user.
     */
    @Override
    public Double calculateRequiredRest(final Double work , final Double rest){
        Double restRequired = 0D;

        if(work > WORK_MIN && work <= WORK_MAX){

            restRequired = REST_WORK_MIN;

        }else if( work > WORK_MAX){

            restRequired = REST_WORK_MAX;

        }

        final Double totalRestCalculate = restRequired - rest;

        return totalRestCalculate <0 ? 0D : totalRestCalculate;
    }

    /**
     * This method aims to inform the calculation factor of the hours worked according to the type of class.
     *
     * @return The factor work
     */
    @Override
    public Double getFactorWork() {
        return 1.0;
    }

}
