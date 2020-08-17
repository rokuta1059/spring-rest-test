package com.springtest.resttest.exception;

import com.springtest.resttest.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice  // 모든 컨트롤러가 실행 시 해당 어노테이션을 가지고있는 핀이 자동 실행
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 오류 발생 시 기본적으로 호출되는 메소드
     * @param ex 메소드에서 발생한 에러 객체
     * @param request 에러가 발생한 위치 정보
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        // ExceptionResponse: 반환하기 위한 에러 값
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        // Server에서 나타나는 기본 오류이므로 500
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * UserNotFoundException이 발생한 경우 호출되는 메소드
     * @param ex 메소드에서 발생한 에러 객체
     * @param request 에러가 발생한 위치 정보
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        // 정보가 존재하지 않는 것이므로 404
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * ResponseEntityExceptionHandler class의 handleMethodArgumentNotValid 메소드를 Override 하여 재정의
     * @param ex 메소드에서 발생한 에러 객체
     * @param headers 헤더 정보
     * @param status 상태 정보
     * @param request 요청값
     * @return 오류 Entity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // 날짜 및 시간, 에러 메세지, 에러 상세 내용
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
