package com.springtest.resttest.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 개발자가 직접 생성하는 것이 아닌
 * 스프링에서 직접 생성하고 관리하도록 컨트롤러를 구성한다다 */
@RestController     // REST 컨트롤러로 사용됨
public class UserController {
    // new 키워드로 생성하는 것이 아닌 의존성 주입을 통해 생성
    // 의존성 설정은 xml, class의 set 함수, 생성자 등을 통해 가능
    // Spring에서 생성되어 관리되는 인스턴스를 Bean이라고 부른다
    // 용도에 따라서 ServiceBean, ControllerBean 등으로 선언한다
    // Spring 또는 IOC 컨테이너에 등록된 Bean은 프로그램 실행 중 변경 불가능
    private UserDaoService service;

    // 생성자를 이용한 의존성 주입
    public UserController(UserDaoService service) {
        this.service = service;
    }

    /**
     * client에서 /users를 호출하게 되면 반환되는 메소드
     * @return
     */
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/{id}
    // 컨트롤러로 id가 전달될 때에는 String으로 전달됨
    // 함수 선언시 int로 선언하게 되면 자동으로 int형으로 변환된다
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return service.findOne(id);
    }
}
