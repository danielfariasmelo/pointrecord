package br.com.liferay.daniel.pointrecord.user.repository;

import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.exception.PointRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public User findById(final String pis) {
       final Optional<User> user = userRepository.findById(pis);

        if (!user.isPresent())
            throw new PointRecordException(messageSource.getMessage("system.information",
                    new String[]{pis}, LocaleContextHolder.getLocale()));

        return user.get();
    }
}
