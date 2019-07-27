package cn.shenghui.tutorial.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/26 14:14
 */
@Data
@ApiModel(value = "create account information")
public class CreateAccountRequest {
    @ApiModelProperty(value = "account name", required = true)
    String accountName;

    @ApiModelProperty(value = "account password", required = true)
    String accountPassword;
}
