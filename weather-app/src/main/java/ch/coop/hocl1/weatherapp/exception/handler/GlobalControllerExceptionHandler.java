package ch.coop.hocl1.weatherapp.exception.handler;

import ch.coop.hocl1.weatherapp.exception.types.BackendDataInvalidException;
import ch.coop.hocl1.weatherapp.models.error.GenericErrorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorModel> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);

        GenericErrorModel restError = new GenericErrorModel();
        restError.setErrorType("UnexpectedError");
        restError.setErrorType("Something bad happened. Contact your administrator");

        return new ResponseEntity<>(restError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BackendDataInvalidException.class)
    public ResponseEntity<GenericErrorModel> handleException(BackendDataInvalidException e) {
        LOGGER.warn(e.getMessage(), e);

        GenericErrorModel restError = new GenericErrorModel();
        restError.setErrorType("BackendDataInvalid");
        restError.setErrorType("Some data from the backend did not have the expected format");

        return new ResponseEntity<>(restError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
