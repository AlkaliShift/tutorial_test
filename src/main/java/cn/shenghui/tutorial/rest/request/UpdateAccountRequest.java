package cn.shenghui.tutorial.rest.request;

import cn.shenghui.tutorial.rest.response.CreateAccountResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/8/7 14:53
 */
@Data
@ApiModel(value = "update account information")
public class UpdateAccountRequest extends CreateAccountRequest {
    @ApiModelProperty(value = "account id", required = true)
    @NotEmpty(message = "账户ID为空")
    String accountId;
}
