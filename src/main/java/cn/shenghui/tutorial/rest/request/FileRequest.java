package cn.shenghui.tutorial.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/31 12:56
 */
@Data
@ApiModel(value = "file")
public class FileRequest {
    @ApiModelProperty(value = "file path")
    String filePath;
}
