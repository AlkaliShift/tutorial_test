package cn.shenghui.tutorial.service;

import cn.shenghui.tutorial.dao.mapper.AccountMapper;
import cn.shenghui.tutorial.dao.model.Account;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * @param account account
     * @return account id
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
     * @param accountId account id
     * @return account info
     */
    public Account getAccountInfo(String accountId){
        return accountMapper.getAccountInfo(accountId);
    }

    /**
     * get all accounts with page
     * @return page info
     */
    public PageInfo<Account> searchAccountPageInfo(String accountId, int page, int limit) {
        PageHelper.startPage(page, limit);
        return new PageInfo<>(accountMapper.searchAccountInfo(accountId));
    }

    /**
     * get all accounts without page
     * @param accountId account id
     * @return account list
     */
    public List<Account> searchAccountInfo(String accountId){
        return accountMapper.searchAccountInfo(accountId);
    }

    /**
     * pay
     * @param accountId account id
     * @param amount amount
     */
    public void pay(String accountId, long amount){
        accountMapper.updateBalance(accountId, amount);
    }

    /**
     * delete account
     * @param accountId account id
     */
    public void deleteAccount(String accountId) {
        accountMapper.deleteAccount(accountId);
    }

    /**
     * update account
     * @param account account
     */
    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
    }

    /**
     * update account path
     * @param accountId account id
     * @param filename filename
     */
    public void updateAccountPath(String accountId, String filename) {
        accountMapper.updateAccountPath(accountId, filename);
    }
}
