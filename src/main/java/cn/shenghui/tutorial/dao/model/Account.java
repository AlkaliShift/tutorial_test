package cn.shenghui.tutorial.dao.model;

import lombok.Data;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/25 22:24
 * Account
 */
@Data
public class Account {
    /**
     * account id
     */
    String accountId;

    /**
     * account name
     */
    String accountName;

    /**
     * password
     */
    String payPassword;

    /**
     * balance
     */
    long balance;
}
