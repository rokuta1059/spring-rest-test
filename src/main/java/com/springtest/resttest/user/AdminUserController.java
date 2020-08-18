package com.springtest.resttest.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 개발자가 직접 생성하는 것이 아닌
 * 스프링에서 직접 생성하고 관리하도록 컨트롤러를 구성한다다 */
@RestController     // REST 컨트롤러로 사용됨
@RequestMapping("/admin")
public class AdminUserController {
    // new 키워드로 생성하는 것이 아닌 의존성 주입을 통해 생성
    // 의존성 설정은 xml, class의 set 함수, 생성자 등을 통해 가능
    // Spring에서 생성되어 관리되는 인스턴스를 Bean이라고 부른다
    // 용도에 따라서 ServiceBean, ControllerBean 등으로 선언한다
    // Spring 또는 IOC 컨테이너에 등록된 Bean은 프로그램 실행 중 변경 불가능
    private UserDaoService service;

    // 생성자를 이용한 의존성 주입
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    /**
     * client에서 /users를 호출하게 되면 반환되는 메소드
     * @return
     */
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // 위에서 선언한 filter 를 사용할 수 있는 형태로 변환
        // addFilter 의 인자는 User class JsonFilter 에서 지정한 값
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // GET /users/{id}
    // 컨트롤러로 id가 전달될 때에는 String으로 전달됨
    // 함수 선언시 int로 선언하게 되면 자동으로 int형으로 변환된다

    /**
     * id에 해당하는 User 를 반환한다
     * filter 가 적용되어 사용될 수 있는 MappingJacksonValue 타입으로 반환한다
     * @param id
     * @return
     */
    // @GetMapping("/v1/users/{id}")
    @GetMapping(value = "/users/{id}/", params = "version=1")  // request parameter를 이용한 버전관리
    public MappingJacksonValue retrieveUser(@PathVariable int id) {

        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // Property 를 이용하여 제어할 수 있도록 SimpleBeanPropertyFilter 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // 위에서 선언한 filter 를 사용할 수 있는 형태로 변환
        // addFilter 의 인자는 User class JsonFilter 에서 지정한 값
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    /**
     * id에 해당하는 User 를 반환한다
     * filter 가 적용되어 사용될 수 있는 MappingJacksonValue 타입으로 반환한다
     * @param id
     * @return
     */
    // @GetMapping("/v2/users/{id}")
    @GetMapping(value = "/users/{id}/", params = "version=2")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {

        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // Property 를 이용하여 제어할 수 있도록 SimpleBeanPropertyFilter 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        // 위에서 선언한 filter 를 사용할 수 있는 형태로 변환
        // addFilter 의 인자는 User class JsonFilter 에서 지정한 값
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }

}
