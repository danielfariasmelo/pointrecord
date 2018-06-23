package br.com.liferay.daniel.pointrecord.exception;

import br.com.liferay.daniel.pointrecord.domain.UnknownError;
import br.com.liferay.daniel.pointrecord.exception.repository.PointExceptionService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.UUID;

@ControllerAdvice
public class PointRecordExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PointExceptionService pointExceptionService;

    /**
     * This method checks manipulates all exceptions thrown by systems, and persist errors in the datasource errors not
     * handled by the system.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error in the process")
    protected String handleException(final Exception ex) {

        if (ex instanceof PointRecordException) {
            return ex.getMessage();
        }

        final UnknownError unknownError = new UnknownError();
        unknownError.setUuid(UUID.randomUUID().toString());
        unknownError.setLogger(ExceptionUtils.getStackTrace(ex));
        unknownError.setDateHour(new Date(System.currentTimeMillis()));
        unknownError.setCause(ex.getCause()!= null ? ex.getCause().getMessage() : "");
        pointExceptionService.save(unknownError);

        return new String(messageSource.getMessage("system.information",
                new String[]{unknownError.getUuid()}, LocaleContextHolder.getLocale()));
    }
}
