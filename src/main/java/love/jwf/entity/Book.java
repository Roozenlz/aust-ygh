package love.jwf.entity;

import lombok.Data;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Data
public class Book {
    /**
     * 书号
     */
    private Long id;
    /**
     * 书名
     */
    private String name;
    /**
     * 著作者
     */
    private String author;
    /**
     * 现存量
     */
    private Integer standingStock;
    /**
     * 库存量
     */
    private Integer stock;
}
