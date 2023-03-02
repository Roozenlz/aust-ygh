package love.jwf.common.algorithm;

import love.jwf.entity.BookInfo;

import java.util.List;

/**
 * 排序算法实现类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
public class Sort {
    /**
     * 根据书号将书籍信息升序排列
     *
     * @param bookInfos 书籍信息集合
     */
    public static void sortBooksById(List<BookInfo> bookInfos) {
        int lo = 0;
        int hi = bookInfos.size() - 1;
        sort(bookInfos, lo, hi);
    }

    /**
     * 快速排序
     *
     * @param bookInfos 书籍信息集合
     * @param lo        起始下标
     * @param hi        终止下标
     */
    private static void sort(List<BookInfo> bookInfos, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        //对bookInfos集合中，从lo到hi的元素进行切分
        int partition = partition(bookInfos, lo, hi);
        //对左边分组中的元素进行排序
        //对右边分组中的元素进行排序
        sort(bookInfos, lo, partition - 1);
        sort(bookInfos, partition + 1, hi);
    }

    /**
     * 对bookInfos集合中，从lo到hi的元素进行切分
     *
     * @param bookInfos 书籍信息集合
     * @param lo        起始下标
     * @param hi        终止下标
     * @return
     */
    private static int partition(List<BookInfo> bookInfos, int lo, int hi) {
        BookInfo key = bookInfos.get(lo); //把最左边的元素当做基准值
        int left = lo; //定义一个左侧指针，初始指向最左边的元素
        int right = hi + 1; //定义一个右侧指针，初始指向左右侧的元素下一个位置
        //进行切分
        while (true) {
            //先从右往左扫描，找到一个比基准值小的元素
            while (less(key, bookInfos.get(--right))) {//循环停止，证明找到了一个比基准值小的元素
                if (right == lo) {
                    break;//已经扫描到最左边了，无需继续扫描
                }
            }
            //再从左往右扫描，找一个比基准值大的元素
            while (less(bookInfos.get(++left), key)) {//循环停止，证明找到了一个比基准值大的元素
                if (left == hi) {
                    break;//已经扫描到了最右边了，无需继续扫描
                }
            }
            if (left >= right) {
                //扫描完了所有元素，结束循环
                break;
            } else {
                //交换left和right索引处的元素
                exch(bookInfos, left, right);
            }
        }
        //交换最后right索引处和基准值所在的索引处的值
        exch(bookInfos, lo, right);
        return right;//right就是切分的界限
    }

    /**
     * 数组元素i和j交换位置
     */
    private static void exch(List<BookInfo> bookInfos, int i, int j) {
        BookInfo t = bookInfos.get(i);
        bookInfos.set(i, bookInfos.get(j));
        bookInfos.set(j, t);
    }

    /**
     * 比较v元素是否小于w元素
     */
    private static boolean less(BookInfo v, BookInfo w) {
        return v.getId() - w.getId() < 0;
    }
}
