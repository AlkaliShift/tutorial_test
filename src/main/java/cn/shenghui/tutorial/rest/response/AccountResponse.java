package cn.shenghui.tutorial.rest.response;

import cn.shenghui.tutorial.base.AbstractResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/27 19:48
 */
@Data
@ApiModel(value = "get account information")
public class AccountResponse extends AbstractResponse {

    /**
     * account id
     */
    String accountId;

    /**
     * account name
     */
    String accountName;

    /**
     * balance
     */
    long balance;
}
