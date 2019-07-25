package cn.shenghui.tutorial.dao.model;

import lombok.Data;

/**
 * @version 1.0
 * @Author: shenghui
 * @Date: 2019/7/25 22:24
 * 账户类
 */
@Data
public class Account {
    /**
     * 账户ID
     */
    String accountId;

    /**
     * 账户名称
     */
    String accountName;

    /**
     * 账户密码
     */
    String payPassword;

    /**
     * 账户金额
     */
    long money;
}
