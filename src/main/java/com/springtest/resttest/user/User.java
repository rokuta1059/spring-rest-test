package com.springtest.resttest.user;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 사용자를 정의하는 클래스
 */
@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"})
@JsonFilter("UserInfo")
public class User {
    private Integer id;

    // 유효성 검사: 길이가 최소 2 이상
    @Size(min=2, message="Name은 2글자 이상 입력해 주세요.")
    private String name;

    // 유효성 검사: 현재 시간보다 이전 시간일 것
    @Past
    private Date joinDate;

    // 외부에 노출시키고 싶지 않은 데이터
    // jackson 패키지에서 불러들여 사용한다
    // @JsonIgnore 어노테이션을 각각 붙여서 사용할수도 있지만
    // class 단위에서 @JsonIgnoreProperties(value={})를 이용할 수도 있다
    private String password;
    private String ssn;

}
