package br.com.liferay.daniel.pointrecord.user.repository;

import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.domain.UserWorkDTO;
import br.com.liferay.daniel.pointrecord.exception.PointRecordException;
import br.com.liferay.daniel.pointrecord.user.work.UserWorkFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserWorkFactory userWorkFactory;

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

    public UserWorkDTO calculateWorkUser(String pis, LocalDateTime periodIni, LocalDateTime periodFin) {
        final User user = this.findById(pis);

        return null;
    }
}
