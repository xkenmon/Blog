package cn.xkenmon.blog.dao;

import cn.xkenmon.blog.vo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    void createUser(User user);

    User findUserById(@Param("id") int id);

    User findUserByName(@Param("name") String name);

    User findUserByEmail(@Param("email") String email);

//    public String findAvatarById()

    int changeLogStatById(@Param("id") int id, @Param("stat") int stat);

    int changeAvatarById(@Param("id") int id, @Param("avt") String avt);
}
