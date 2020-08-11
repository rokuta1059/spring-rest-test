package com.springtest.resttest;

// lombok을 사용하여 메소드를 자동으로 생성하도록 하자
// 어노테이션 입력 후 lombok으로 생성
// Build, Execution, Deployment > Compiler > Annotation Processors에서
// Enable annotation processing을 활성화하자

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor     // 모든 Args를 가지고 있는 Constructor
// @NoArgsConstructor      매개변수가 아무것도 없는 생성자
public class HelloWorldBean {

    private String message;

    // @AllArgsConstructor가 있는 경우 수동으로 만들 필요가 없다
    // 둘 다 있는 경우 빌드 과정에서 오류 발생
//    public HelloWorldBean(String message) {
//        this.message = message;
//    }

    // 수동으로 변수 생성 시 아래 함수도 만들자
//    public String getMessage() {
//        return this.message;
//    }
//
//    public void setMessage(String msg) {
//        this.message = msg;
//    }
}
