package love.jwf.service;

import love.jwf.entity.BorrowerInfo;

import java.util.List;

/**
 * 借阅信息的业务逻辑接口
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
public interface BorrowService {
    /**
     * 增加借阅信息
     *
     * @param borrowerInfo 书籍信息实例
     */
    void add(BorrowerInfo borrowerInfo);

    /**
     * 注销借阅信息
     *
     * @param id 借阅信息的id
     * @return 返回注销是否成功
     */
    boolean back(Integer id);

    /**
     * 获取所有的借阅信息
     *
     * @return 所有的借阅信息
     */
    List<BorrowerInfo> selectAll();

    /**
     * 根据借阅者的书证号查询读者的借阅信息
     *
     * @param id 书证号
     * @return 读者的借阅信息
     */
    List<BorrowerInfo> selectByBorrowerId(Long id);

    /**
     * 根据书籍信息id查询借阅信息
     *
     * @param id 书籍信息id
     * @return 借阅信息
     */
    BorrowerInfo selectById(Integer id);

    /**
     * 查询超出归还期限的图书借阅信息
     *
     * @return 超出归还期限的图书借阅信息
     */
    List<BorrowerInfo> selectBeyond();
}
