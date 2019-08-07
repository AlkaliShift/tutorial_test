package cn.shenghui.tutorial.dao.mapper;

import cn.shenghui.tutorial.dao.model.Account;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/27 13:58
 */
@Mapper
public interface AccountMapper {

    /**
     * insert
     * @param account
     */
    void createAccount(Account account);

    /**
     * get account information
     * @param accountId
     * @return
     */
    Account getAccountInfo(String accountId);

    /**
     * get all accounts
     * @return
     */
    List<Account> getAllAccount();

    /**
     * update balance
     * @param accountId
     * @param amount
     * @return
     */
    void updateBalance(@Param("accountId") String accountId, @Param("amount") long amount);

    /**
     * delete account
     * @param accountId
     */
    void deleteAccount(String accountId);

    /**
     * update account
     * @param account
     */
    void updateAccount(Account account);
}
