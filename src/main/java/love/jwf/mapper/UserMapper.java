package love.jwf.mapper;

/**
 * 系统用户的持久化接口
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
public interface UserMapper {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    boolean signup(String username, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    boolean login(String username, String password);
}
