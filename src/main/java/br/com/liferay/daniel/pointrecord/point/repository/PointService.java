package br.com.liferay.daniel.pointrecord.point.repository;

import br.com.liferay.daniel.pointrecord.domain.Clockin;
import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.exception.PointRecordException;
import br.com.liferay.daniel.pointrecord.user.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service  public class PointService {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;


    /**
     * Creates a record based on the entity passed as parameter. To finalize the transaction
     * this method validates whether a user exists and whether the time for the record is enabled.
     *
     * @param clockin
     * @return point object
     */
    public ResponseEntity<Point> register(Clockin clockin) {

        final User user = userService.findById(clockin.getPis());

        if(!pointRepository.existsPointByUserAndDateTime(user,clockin.getDateTime()))
        {
            final Point pointResponse = pointRepository.save(new Point(clockin.getDateTime(),user));

            this.validRoundTime(pointResponse.getDateTime());

            return new ResponseEntity<>(pointResponse,HttpStatus.OK);

        }else{

            throw new PointRecordException(messageSource.getMessage("register.already.exists",
                    new String[]{user.getName(),clockin.getDateTime().format(DateTimeFormatter.BASIC_ISO_DATE)},
                    LocaleContextHolder.getLocale()));
        }

    }

    /**
     * This method checks whether a beat already exists and whether the time for logging is enabled.
     *
     * @param localDateTime
     */
    public void validRoundTime(final LocalDateTime localDateTime){

        if(pointRepository.roundTime(localDateTime.minusMinutes(1),localDateTime)>1){
            throw new PointRecordException(messageSource.getMessage("register.invalid.time",
                    new String[]{}, LocaleContextHolder.getLocale()));
        }
    }
}
