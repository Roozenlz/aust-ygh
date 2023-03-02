package love.jwf.common.algorithm;

import love.jwf.entity.BookInfo;

import java.util.List;

/**
 * 用于查找的类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
public class Search {
    /**
     * 根据书号进行二分查找
     *
     * @param bookInfos 书籍信息集合
     * @param id        书号
     * @return 查找到的书籍对象，找不到则返回空
     */
    public static BookInfo searchBookById(List<BookInfo> bookInfos, Long id) {
        int left = 0, right = bookInfos.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (bookInfos.get(mid).getId() > id) {
                right = mid - 1;
            } else if (bookInfos.get(mid).getId() < id) {
                left = mid + 1;
            } else {
                return bookInfos.get(mid);
            }
        }
        return null;
    }
}
