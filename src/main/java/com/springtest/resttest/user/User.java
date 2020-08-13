package com.springtest.resttest.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 사용자를 정의하는 클래스
 */
@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Date joinDate;
}
