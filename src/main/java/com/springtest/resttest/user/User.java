package com.springtest.resttest.user;

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
public class User {
    private Integer id;

    // 유효성 검사: 길이가 최소 2 이상
    @Size(min=2, message="Name은 2글자 이상 입력해 주세요.")
    private String name;

    // 유효성 검사: 현재 시간보다 이전 시간일 것
    @Past
    private Date joinDate;
}
