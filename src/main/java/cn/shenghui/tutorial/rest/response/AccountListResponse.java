package cn.shenghui.tutorial.rest.response;

import cn.shenghui.tutorial.base.AbstractResponse;
import cn.shenghui.tutorial.dao.model.Account;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/8/7 10:13
 */
@Data
@ApiModel(value = "accout list")
public class AccountListResponse extends AbstractResponse {
    protected List<Account> accounts;

    long total;

    public void setAccountList(List<Account> accounts){
        this.setAccounts(accounts);
    }
}
