package love.jwf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import love.jwf.common.R;
import love.jwf.entity.BookInfo;
import love.jwf.entity.BorrowerInfo;
import love.jwf.entity.User;
import love.jwf.service.BookService;
import love.jwf.service.BorrowService;
import love.jwf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 系统用户控制器
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api("用户")
public class UserController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public R<String> login(@RequestBody User user) {
        log.info("username=" + user.getUsername() + ",password=" + user.getPassword());
        return R.success("登陆成功");
    }

    @PostMapping("/count/{count}")
    @ApiOperation("采编入库")
    @CrossOrigin
    public R<String> save(@RequestBody BookInfo bookInfo, @PathVariable Integer count) {
        BookInfo selectById = bookService.selectById(bookInfo.getId());
        if (selectById == null) {
            bookInfo.setStock(count);
            bookInfo.setStandingStock(count);
            bookService.insert(bookInfo);
            return R.success("入库成功！");
        } else {
            bookInfo.setStock(selectById.getStock() + count);
            bookInfo.setStandingStock(selectById.getStandingStock() + count);
            bookService.update(bookInfo);
            return R.success("入库成功，书号已存在，仅改变库存量！");
        }
    }

    @PostMapping("/borrow")
    @ApiOperation("借阅")
    public R<String> borrow(@RequestBody BorrowerInfo borrowerInfo) {
        BookInfo bookInfo = bookService.selectById(borrowerInfo.getBookId());
        if (bookInfo == null) {
            return R.error("书籍不存在！");
        }
        if (bookInfo.getStandingStock() <= 0) {
            return R.error("书籍现存量不足！");
        }
        bookInfo.setStandingStock(bookInfo.getStandingStock() - 1);
        borrowerInfo.setBorrowDateTime(LocalDateTime.now());
        borrowService.add(borrowerInfo);
        return R.success("借出成功！");
    }

    @PutMapping("/back/{id}")
    @ApiOperation("归还")
    public R<String> back(@PathVariable Integer id) {
        BorrowerInfo borrowerInfo = borrowService.selectById(id);
        if (borrowerInfo == null) {
            return R.error("借阅信息不存在！");
        }
        BookInfo bookInfo = bookService.selectById(borrowerInfo.getBookId());
        bookInfo.setStandingStock(bookInfo.getStandingStock() + 1);
        borrowService.back(id);
        return R.success("归还成功！");
    }

}
