package love.jwf.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 借阅信息类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "借阅信息")
public class BorrowerInfo {
    @ApiModelProperty("借阅信息id")
    private Integer id;
    @ApiModelProperty("书证号")
    private Long BorrowerId;
    @ApiModelProperty("借出的书")
    private Long bookId;
    @ApiModelProperty("归还期限(天)")
    private Integer dayNumber;
    @ApiModelProperty("借出时间")
    private LocalDateTime borrowDateTime;
    @ApiModelProperty("归还时间(未归还时为NULL)")
    private LocalDateTime backDateTime;
}
