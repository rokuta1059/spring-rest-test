package com.springtest.resttest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 예외처리를 사용하기 위해 만든 객체
// 특정한 예외를 측정하는 예외처리가 아닌 일반화된 예외 클래스를 생성하여 사용
// ExceptionResponse라는 자바 객체를 만들어서 사용
// AOP
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date errortime;
    private String message;
    private String details;
}
