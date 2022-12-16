package love.jwf.service.impl;

import love.jwf.entity.BorrowerInfo;
import love.jwf.mapper.BorrowMapper;
import love.jwf.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 借阅信息的业务逻辑类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public void add(BorrowerInfo borrowerInfo) {
        borrowMapper.insert(borrowerInfo);
    }

    @Override
    public boolean back(Integer id) {
        return borrowMapper.update(id);
    }

    @Override
    public List<BorrowerInfo> selectAll() {
        return borrowMapper.selectAll();
    }

    @Override
    public List<BorrowerInfo> selectByBorrowerId(Long id) {
        return borrowMapper.selectByBorrowerId(id);
    }

    @Override
    public BorrowerInfo selectById(Integer id) {
        return borrowMapper.selectById(id);
    }

    @Override
    public List<BorrowerInfo> selectBeyond() {
        return borrowMapper.selectBeyond();
    }
}
