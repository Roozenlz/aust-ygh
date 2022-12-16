package love.jwf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 系统用户类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
}
