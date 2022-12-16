package love.jwf.mapper;

import love.jwf.entity.BorrowerInfo;

import java.util.List;

/**
 * 借阅信息的持久化接口
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
public interface BorrowMapper {
    /**
     * 添加借阅信息
     *
     * @param borrowerInfo 借阅信息实例
     */
    void insert(BorrowerInfo borrowerInfo);

    /**
     * 修改借阅信息为已归还
     *
     * @param id 借阅信息id
     * @return 修改的结果
     */
    boolean update(Integer id);

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
