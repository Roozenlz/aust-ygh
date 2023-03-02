package love.jwf.service.impl;

import love.jwf.mapper.UserMapper;
import love.jwf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户的业务逻辑类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public boolean signup(String username, String password) {
        return userMapper.signup(username, password);
    }
}
