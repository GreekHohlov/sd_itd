package ru.sber.spring.java13springmy.sdproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sber.spring.java13springmy.sdproject.constants.Errors;

@RestControllerAdvice
public class ExceptionTranslator {
    @ExceptionHandler(MyDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMyDeleteException(MyDeleteException ex) {
        return proceedFieldsErrors(ex, Errors.REST.DELETE_ERROR_MESSAGE, ex.getMessage());
    }
    
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleAuthException(AuthenticationException ex) {
        return proceedFieldsErrors(ex, Errors.REST.AUTH_ERROR_MESSAGE, ex.getMessage());
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleAuthException(AccessDeniedException ex) {
        return proceedFieldsErrors(ex, Errors.REST.AUTH_ERROR_MESSAGE, ex.getMessage());
    }
    
    
    private ErrorDTO proceedFieldsErrors(Exception ex,
                                         String error,
                                         String description) {
        ErrorDTO errorDTO = new ErrorDTO(error, description);
        errorDTO.add(ex.getClass().getName(), "", errorDTO.getMessage());
        return errorDTO;
    }
}
