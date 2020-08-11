package com.springtest.resttest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // 어노테이션을 반드시 입력해 주어야 함
public class HelloWorldController {
    // GET 방식의 메소드 형태로 제작
    // url : /hello-world (endpoint)
    //
    // GET 방식을 선언하는 어노테이션
    // 추가 정보 필요 시 어떤 속성인지 지정해주어야 함
    // ex) path = "/hello-world"
    // 이전에는 @RequestMapping(method=RequestMethod.GET, path="/hello-world")을 사용했다
    @GetMapping("/hello-world")
    public String helloworld() {
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloworldBean() {
        // 반환 형태는 Bean 형태
        // Spring에서는 이를 JSON 형태로 자동으로 반환함
        return new HelloWorldBean("Hello World");
    }
}
