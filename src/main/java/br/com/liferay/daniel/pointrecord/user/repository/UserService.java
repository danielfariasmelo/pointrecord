package br.com.liferay.daniel.pointrecord.user.repository;

import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;
import br.com.liferay.daniel.pointrecord.exception.PointRecordException;
import br.com.liferay.daniel.pointrecord.point.repository.PointService;
import br.com.liferay.daniel.pointrecord.user.work.UserWorkFactory;
import br.com.liferay.daniel.pointrecord.user.work.WorkCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserWorkFactory userWorkFactory;

    @Autowired
    private PointService pointService;

    /**
     * This method checks for a valid user
     *
     * @param pis
     * @return valid user
     */
    public User findById(final String pis) {
        final Optional<User> user = userRepository.findById(pis);

        if (!user.isPresent())
            throw new PointRecordException(messageSource.getMessage("user.not.found",
                    new String[]{pis}, LocaleContextHolder.getLocale()));

        return user.get();
    }

    public UserWorkDTO calculateWorkUser(String pis, LocalDate periodIni, LocalDate periodFin) {

        final UserWorkDTO userWorkDTO = new UserWorkDTO();
        final User user = findById(pis);
        userWorkDTO.setUser(user);

        do{
            final LocalDate registerDay = periodIni;

            final List<LocalDateTime> registersDay = pointService.findAllByUser(pis).stream()
                    .filter(register -> register.getDateTime().getDayOfMonth() == registerDay.getDayOfMonth())
                    .map(register-> register.getDateTime())
                    .collect(Collectors.toList());

            if(registersDay.size()>0){
                final WorkCalculator workCalculator = userWorkFactory.getWorkCalculator(registersDay.get(0));
                final UserWorkDTO work = workCalculator.calculate(registersDay);
                userWorkDTO.setWork(userWorkDTO.getWork() + work.getWork());
                userWorkDTO.setRest(userWorkDTO.getRest() + work.getRest());
            }

            periodIni = periodIni.plusDays(1l);

        }while (!periodFin.isBefore(periodIni));


        return userWorkDTO;
    }
}
