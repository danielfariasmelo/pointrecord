package br.com.liferay.daniel.pointrecord.user.repository;

import br.com.liferay.daniel.pointrecord.domain.ResultDTO;
import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.domain.UserWork;
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
import java.util.ArrayList;
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
     * This method aims to find a valid user in the system database through the parameter pis passed in this method.
     * In addition, validation will occur for cases where there are no users in the database thus returning a runtime
     * exception handled by the PointRecordException.
     *
     * @param pis
     * @return The object User
     */
    public User findById(final String pis) {
        final Optional<User> user = userRepository.findById(pis);

        if (!user.isPresent())
            throw new PointRecordException(messageSource.getMessage("user.not.found",
                    new String[]{pis}, LocaleContextHolder.getLocale()));

        return user.get();
    }

    /**
     * This method has the purpose of calculating hours worked according to the user and the period passed by parameter
     * for this method. In addition to performing the calculation of the hours worked, the calculation of the necessary
     * rest of the user is performed. Finally a ResultDTO object will be returned that contains all the data related to
     * the searched user and a list of details about the hours worked and their respective breaks.
     *
     *
     * @param pis
     * @param periodIni
     * @param periodFin
     * @return The object ResultDTO with contains the User, a list of UserWork and required time rest.
     */
    public ResultDTO calculateWorkUser(String pis, LocalDate periodIni, LocalDate periodFin) {
        final ResultDTO resultDTO = new ResultDTO();
        resultDTO.setUser(findById(pis));

        final List<UserWork> userWorkList = new ArrayList<>();

        do{
            final LocalDate registerDay = periodIni;

            final List<LocalDateTime> registersDay = pointService.findAllByUser(pis).stream()
                    .filter(register -> register.getDateTime().getDayOfMonth() == registerDay.getDayOfMonth())
                    .map(register-> register.getDateTime())
                    .sorted()
                    .collect(Collectors.toList());

            if(registersDay.size()>0){
                final WorkCalculator workCalculator = userWorkFactory.getWorkCalculator(registersDay.get(0));
                final UserWork workResult = workCalculator.calculate(registersDay);

                userWorkList.add(workResult);
            }

            periodIni = periodIni.plusDays(1l);

        }while (!periodFin.isBefore(periodIni));

        resultDTO.setUserWorkList(userWorkList);
        resultDTO.setWorkPeriod(userWorkList.stream().mapToDouble(userWork->userWork.getWork()).sum());
        return resultDTO;
    }
}
