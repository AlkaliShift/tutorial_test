package cn.shenghui.tutorial.rest.response;

import cn.shenghui.tutorial.base.AbstractResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/31 12:58
 */
@Data
@ApiModel(value = "basic response")
public class FileResponse extends AbstractResponse {
    @ApiModelProperty(value = "line")
    int line;

    @ApiModelProperty(value = "wrong lines")
    int wrong;
}
