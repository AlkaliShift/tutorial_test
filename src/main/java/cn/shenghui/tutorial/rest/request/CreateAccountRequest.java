package cn.shenghui.tutorial.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/26 14:14
 */
@Data
@ApiModel(value = "create account information")
public class CreateAccountRequest {
    @ApiModelProperty(value = "account name", required = true)
    @NotBlank(message = "账户名称为空")
    String accountName;

    @ApiModelProperty(value = "account password", required = true)
    @NotBlank(message = "账户密码为空")
    String accountPassword;

    @ApiModelProperty(value = "captcha", required = true)
    @NotBlank(message = "验证码为空")
    String captcha;
}
