package cn.shenghui.tutorial.rest.response;

import cn.shenghui.tutorial.base.AbstractResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/8/7 14:04
 */
@Data
@ApiModel(value = "delete account")
public class BasicAccountResponse extends AbstractResponse {
}
