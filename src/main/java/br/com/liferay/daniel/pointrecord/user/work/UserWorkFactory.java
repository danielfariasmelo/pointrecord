package br.com.liferay.daniel.pointrecord.user.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserWorkFactory {

    @Autowired
    @Qualifier("WorkCalculatorSaturday")
    private WorkCalculator workCalculatorSaturday;

    @Autowired
    @Qualifier("WorkCalculatorSunday")
    private WorkCalculator workCalculatorSunday;

    @Autowired
    @Qualifier("WorkCalculatorWeek")
    private WorkCalculator workCalculatorWeek;


        public WorkCalculator getWorkCalculator (final LocalDateTime localDateTime){

            switch (localDateTime.getDayOfWeek()){

                case SATURDAY:
                    return workCalculatorSaturday;
                case SUNDAY:
                    return workCalculatorSunday;
                default:
                    return workCalculatorWeek;

            }
        }




}
