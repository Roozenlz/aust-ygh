package love.jwf.controller;

import love.jwf.common.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/login")
    public R<String> login(@RequestBody String username, @RequestBody String password){


        return R.success("登陆成功");
    }
}
