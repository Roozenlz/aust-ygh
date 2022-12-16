package love.jwf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 书籍信息类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "书籍信息")
public class BookInfo {
    @ApiModelProperty("书号")
    private Long id;
    @ApiModelProperty("书名")
    private String name;
    @ApiModelProperty("著作者")
    private String author;
    @ApiModelProperty("现存量")
    private Integer standingStock;
    @ApiModelProperty("库存量")
    private Integer stock;
}
