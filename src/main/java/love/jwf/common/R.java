package love.jwf.common;

import lombok.Data;

/** 前后端json数据格式的约定
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Data
public class R<T> {
    /**
     * 结果代码: 1表示成功 || 0及其他表示失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 请求结果成功时的静态泛型工厂方法
     * @param data 数据对象
     * @param <T> 数据类型
     * @return R<T> 的实例
     */
    public static <T> R<T> success(T data) {
        R<T> r = new R<T>();
        r.code = 1;
        r.data = data;
        return r;
    }

    /**
     * 请求结果失败时的静态泛型工厂方法
     * @param msg 错误信息
     * @param <T> 数据类型
     * @return R<T> 的实例
     */
    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.code = 0;
        r.msg = msg;
        return r;
    }
}
