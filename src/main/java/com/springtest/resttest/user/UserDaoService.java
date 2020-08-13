package com.springtest.resttest.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 사용자 관련 로직이 저장된 클래스
 * 메모리에 데이터를 작성하고 테스트한다
 */
public class UserDaoService {
    private static List<User> users = new ArrayList<User>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Kim", new Date()));
        users.add(new User(2, "Sam", new Date()));
        users.add(new User(3, "David", new Date()));
    }

    /**
     * 전체 사용자를 조회한다
     * @return 전체 사용자가 저장된 ArrayList
     */
    public List<User> findAll() {
        return users;
    }

    /**
     * 입력된 사용자를 저장한다
     * @param user 저장하려는 사용자 정보
     * @return 저장이 완료된 사용자 정보
     */
    public User save(User user) {
        // id가 존재하지 않는 user인 경우 id를 부여한다
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    /**
     * 특정 아이디의 사용자를 조회한다
     * @param id 조회하려는 사용자의 id
     * @return 해당 id의 user, 없는 경우 null
     */
    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }
}
