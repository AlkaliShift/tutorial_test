package cn.shenghui.tutorial.service;

import cn.shenghui.tutorial.dao.mapper.AccountMapper;
import cn.shenghui.tutorial.dao.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/26 15:02
 * service layer
 */
@Service
public class AccountService {

    private AccountMapper accountMapper;

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * create account id
     * @param account
     * @return
     */
    public String createAccount(Account account){
        String accountId = UUID.randomUUID().toString();
        account.setAccountId(accountId);
        account.setBalance(1000L);
        accountMapper.createAccount(account);
        return accountId;
    }

    /**
     * get account information
     * @param accountId
     * @return
     */
    public Account getAccountInfo(String accountId){
        return accountMapper.getAccountInfo(accountId);
    }

    /**
     * pay
     * @param accountId
     * @param amount
     * @return
     */
    public void pay(String accountId, long amount){
        accountMapper.updateBalance(accountId, amount);
    }
}
