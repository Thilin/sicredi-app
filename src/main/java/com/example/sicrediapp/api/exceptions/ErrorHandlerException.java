package com.example.sicrediapp.api.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandlerException extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno no sistema. " +
            "Tente novamente, se o problema persistir, entre em contato com o administrador.";

    @Autowired
    private MessageSource messageSource;

    private Error.ErrorBuilder createErrorBuilder(HttpStatus status, ExceptionsEnum exception, String message){
        return Error.builder().timestamp(OffsetDateTime.now()).status(status.value()).type(exception.getUri())
                .title(exception.getDescription()).msg(message);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        return getObjectResponseEntity(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return getObjectResponseEntity(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> getObjectResponseEntity(Exception ex, HttpHeaders headers, HttpStatus status,
                                                           WebRequest request, BindingResult bindingResult) {
        ExceptionsEnum errorType = ExceptionsEnum.INVALID_DATA;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        List<Error.Field> fieldList = bindingResult.getAllErrors().stream()
                .map(fieldError -> {
                    String msg = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    String name = fieldError.getObjectName();
                    if (fieldError instanceof FieldError) {
                        name = ((FieldError) fieldError).getField();
                    }
                    return Error.Field.builder().name(name).msg(msg).build();
                }).collect(Collectors.toList());
        Error errors = createErrorBuilder(status, errorType, detail).msg(detail).fields(fieldList).build();
        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleException(SQLIntegrityConstraintViolationException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionsEnum errorType = ExceptionsEnum.INVALID_DATA;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(CountVoteSessionOpenException.class)
    public ResponseEntity<Object> handleException(CountVoteSessionOpenException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionsEnum errorType = ExceptionsEnum.COUNT_VOTE_SESSION_OPEN;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DuplicateCPFException.class)
    public ResponseEntity<Object> handleException(DuplicateCPFException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionsEnum errorType = ExceptionsEnum.DUPLICATE_CPF;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ExternalServiceUnavailableException.class)
    public ResponseEntity<Object> handleException(ExternalServiceUnavailableException ex, WebRequest request){
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        ExceptionsEnum errorType = ExceptionsEnum.EXTERNAL_SERVICE_UNAVAILABLE;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidSessionDurationException.class)
    public ResponseEntity<Object> handleException(InvalidSessionDurationException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionsEnum errorType = ExceptionsEnum.INVALID_SESSION_DURATION;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleException(ObjectNotFoundException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionsEnum errorType = ExceptionsEnum.RESOURCE_NOT_FOUND;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SessionClosedException.class)
    public ResponseEntity<Object> handleException(SessionClosedException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionsEnum errorType = ExceptionsEnum.SESSION_CLOSED;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UnableToVoteException.class)
    public ResponseEntity<Object> handleException(UnableToVoteException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionsEnum errorType = ExceptionsEnum.UNABLE_TO_VOTE;
        String msg = ex.getMessage();
        Error errors = createErrorBuilder(status, errorType, msg).build();
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }
}
