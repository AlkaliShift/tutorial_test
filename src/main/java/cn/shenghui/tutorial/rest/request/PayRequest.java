package cn.shenghui.tutorial.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/29 09:05
 */
@Data
@ApiModel(value = "pay")
public class PayRequest {
    @ApiModelProperty(value = "account id", required = true)
    String accountId;

    @ApiModelProperty(value = "payment amount", required = true)
    long amount;
}
