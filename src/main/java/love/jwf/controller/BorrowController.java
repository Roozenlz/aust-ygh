package love.jwf.controller;

import io.swagger.annotations.ApiOperation;
import love.jwf.common.R;
import love.jwf.entity.BorrowerInfo;
import love.jwf.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 借阅信息控制器
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@RestController
@RequestMapping("/borrow")
@CrossOrigin
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping
    @ApiOperation("获取所有的借阅信息")
    public R<List<BorrowerInfo>> list() {
        return R.success(borrowService.selectAll());
    }

    @GetMapping("/borrower_id/{id}")
    @ApiOperation("根据借阅者的书证号查询借阅信息")
    public R<List<BorrowerInfo>> getByBorrowId(@PathVariable Long id) {
        return R.success(borrowService.selectByBorrowerId(id));
    }

    @GetMapping("/beyond")
    @ApiOperation("查询超出归还期限的图书借阅信息")
    public R<List<BorrowerInfo>> beyond() {
        return R.success(borrowService.selectBeyond());
    }
}
