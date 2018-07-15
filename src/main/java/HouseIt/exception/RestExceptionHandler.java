package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<java.lang.Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing Path Variable Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, String.format("Invalid URL request for %s", request.getContextPath()), e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Missing Servlet Request Parameter Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Parameter %s is missing", e.getParameterName()), e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Http Message Not Readable Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Could not write JSON", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Http Message Not Readable Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Method Argument Not Valid Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation error", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        logger.error("Data Integrity Violation Exception: ", e);
        ErrorResponse response = null;

        if (e.getCause() instanceof ConstraintViolationException) {
            response = new ErrorResponse(HttpStatus.CONFLICT, "Database error", e);
        } else {
            response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e);
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({MyEntityNotFoundException.class})
    protected ResponseEntity<Object> handleMyResourceNotFound(MyEntityNotFoundException e) {
        logger.error("My Resource Not Found Exception ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, "Entity not found", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        logger.error("Entity Not Found Exception ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, "Entity not found", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        logger.error("Access is denied ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "You don't have access to this", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        logger.error("Access is denied ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "You don't have access to this", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    protected ResponseEntity<Object> handleInsufficientAuthenticationException(InsufficientAuthenticationException e, WebRequest request) {
        logger.error("Access is denied ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "You don't have access to this", e);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternal(RuntimeException e, WebRequest request) {
        logger.error("Runtime Exception: ", e);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", e);

        return new ResponseEntity<Object>(response, response.getStatus());
    }

}