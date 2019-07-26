package cn.shenghui.tutorial.rest.response;

import cn.shenghui.tutorial.base.AbstractResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @Author: shenghui
 * @Date: {2019/7/26} {14:18}
 */
@Data
@ApiModel(value = "account basic information")
public class CreateAccountResponse extends AbstractResponse {
    @ApiModelProperty(value = "account id", required = true)
    String accountId;
}
