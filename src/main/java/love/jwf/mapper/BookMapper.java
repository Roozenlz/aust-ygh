package love.jwf.mapper;

import love.jwf.entity.BookInfo;

import java.util.List;

/**
 * 书籍信息的持久化接口
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
public interface BookMapper {
    /**
     * 增加书籍信息
     *
     * @param bookInfo 书籍信息实例
     */
    void insert(BookInfo bookInfo);

    /**
     * 删除书籍信息
     *
     * @param id 书号
     * @return 返回删除是否成功
     */
    boolean delete(Long id);

    /**
     * 更改书籍信息
     *
     * @param bookInfo 书籍信息实例
     * @return 返回影响到的书籍信息的字段数
     */
    int update(BookInfo bookInfo);

    /**
     * 根据书号查找书籍信息
     *
     * @param id 书号
     * @return 查到时返回书籍信息实例，查不到时返回null
     */
    BookInfo selectById(Long id);

    /**
     * 根据书名查找书籍信息
     * 支持模糊查找
     *
     * @param name 书名
     * @return 查到时返回书籍信息实例的List，查不到时返回空List
     */
    List<BookInfo> selectByName(String name);

    /**
     * 根据作者查找书籍信息
     * 当且仅当作者名完全匹配时返回查询结果
     *
     * @param author 作者
     * @return 查到时返回书籍信息实例的List，查不到时返回空List
     */
    List<BookInfo> selectByAuthor(String author);

    /**
     * 查询所有
     *
     * @return 所有书籍信息
     */
    List<BookInfo> selectAll();
}
