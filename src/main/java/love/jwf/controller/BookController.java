package love.jwf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.jwf.common.R;
import love.jwf.entity.BookInfo;
import love.jwf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书籍信息控制器
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/book")
@Api("bookController")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping()
    @ApiOperation("获取所有书籍信息")
    public R<List<BookInfo>> getAll() {
        return R.success(bookService.selectAll());
    }

    @GetMapping("/id/{id}")
    @ApiOperation("根据书号查找书籍信息")
    public R<BookInfo> getById(@PathVariable Long id) {
        return R.success(bookService.selectById(id));
    }

    @GetMapping("/name/{name}")
    @ApiOperation("根据书名查找书籍信息")
    public R<List<BookInfo>> getByName(@PathVariable String name) {
        return R.success(bookService.selectByName(name));
    }

    @GetMapping("/author/{author}")
    @ApiOperation("根据作者查找书籍信息")
    public R<List<BookInfo>> getByAuthor(@PathVariable String author) {
        return R.success(bookService.selectByAuthor(author));
    }
}


