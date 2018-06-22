package br.com.liferay.daniel.pointrecord.exception.repository;

import br.com.liferay.daniel.pointrecord.domain.UnknownError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class PointExceptionService {

    @Autowired
    private PointExceptionRepository pointExceptionRepository;

    /**
     * This method is responsible for save the all errors in the systems
     * @param unknownError
     */
    public void save(UnknownError unknownError) {
        pointExceptionRepository.save(unknownError);
    }
}
