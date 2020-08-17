package com.springtest.resttest.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController // 어노테이션을 반드시 입력해 주어야 함
public class HelloWorldController {

    // 동일한 타입의 Bean 을 자동으로 주입하여 준다
    @Autowired
    private MessageSource messageSource;

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

    // 가변 데이터를 가지고 있다
    // 변수 옆에 반드시 @PathVariable를 붙여주어 사용 가능하도록 한다
    // 오버로딩
    // 다른 값을 사용하고 싶은 경우 @PathVariable(value = "name")와 같이 사용
    @GetMapping("/hello-world-bean/{name}")
    public HelloWorldBean helloworldBean(@PathVariable String name) {

        return new HelloWorldBean(String.format("Hello, %s!", name));
    }

    /**
     * 다국어 처리 테스트용 메소드
     * 헤더에 들어있는 언어값을 이용하여 해당 언어에 맞는 인사말을 출력한다다
     *@param locale
     * @return
     */
    @GetMapping("/hello-world-international")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false) Locale locale) {
        // 선언한 리소스를 통해 메세지를 받아온다
        // Key값, (Key값이 가변변수를 가지고있는 문자열인 경우) 파라메터, locale값
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
