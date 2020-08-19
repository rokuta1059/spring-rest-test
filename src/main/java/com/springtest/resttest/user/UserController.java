package com.springtest.resttest.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<User> retrieveUser(@PathVariable int id) {

        // return service.findOne(id);
        // 기존 방식의 경우 존재하지 않는 id를 호출 시 null이 반환된다
        // 이 경우 null을 반환하지만 어플리케이션 작동에는 문제가 없다는 200 status가 반환되게 된다

        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS 구현
        EntityModel<User> resource = new EntityModel<>(user);
        // retrieveAllUsers 메소드와 all-users 를 연결 하이퍼미디어로 사용
        WebMvcLinkBuilder linkTo = linkTo(
                methodOn(this.getClass()).retrieveAllUsers()
        );
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    /**
     * POST /users
     * /users 호출 시 POST로 호출하게 되며, 해당하는 user를 추가한다.
     * @RequestBody 를 이용하여 값을 Body에서 받아온다
     * @Valid 를 통하여 유효성 검사를 진행한다
     * @param user 추가하려는 user
     * @return 추가 완료된 user
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // 사용자에게 요청값을 반환하기 위해 ServletUriComponentsBuilder를 사용한다
        // 새로운 유저가 추가된 경우 상황에 맞는 상태값이 반환되며
        // headers의 location값에 생성 완료된 사용자의 URL이 전달된다
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()                       // 현재 가지고있는 Request값을 사용
                .path("/{id}")                              // 반환값 지정
                .buildAndExpand(savedUser.getId())          // 반환값에서 지정한 가변 변수 지정
                .toUri();                                   // 모든 형태를 URI 형태로 변경
        return ResponseEntity.created(location).build();
    }

    // DELETE /users/{id}
    // DELETE로 호출 시 실행
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteUserById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    // PUT /users
    // PUT로 호출 시 실행
    @PutMapping("/users")
    public void changeUserName(@RequestBody User inputUser) {
        User user = service.changeUserNameById(inputUser.getId(), inputUser.getName());

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
        }
    }
}
