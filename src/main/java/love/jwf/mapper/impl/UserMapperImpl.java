package love.jwf.mapper.impl;

import love.jwf.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * 系统用户的持久化类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Repository
public class UserMapperImpl implements UserMapper {

    private String username;
    private String password;

    @Override
    public boolean signup(String username, String password) {
        this.username = username;
        this.password = password;
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        //如果用户名和密码为空或者不匹配，则登录失败
        if (username == null || !username.equals(this.username)) {
            return false;
        }
        if (password == null || !password.equals(this.password)) {
            return false;
        }
        return true;
    }
}
