package com.springtest.resttest.user;

// HTTP Status code
// 2XX -> OK
// 4XX -> Client 클라이언트에서 오류가 발생한 경우 (권한, 존재하지 않는 메소드 등)
// 5XX -> Server 서버에서 오류가 발생한 경우

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 사용자가 존재하지 않는 경우 발생하는 예외
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
