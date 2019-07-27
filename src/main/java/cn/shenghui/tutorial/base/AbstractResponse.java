package cn.shenghui.tutorial.base;

import lombok.Data;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/26 14:21
 * http basic response
 */
@Data
public abstract class AbstractResponse {
    protected int statusCode;

    protected String msg;

    public void setStatusInfo(int statusCode, String msg){
        this.setStatusCode(statusCode);
        this.setMsg(msg);
    }
}
