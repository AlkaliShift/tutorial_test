package cn.shenghui.tutorial.dao.mapper;

import cn.shenghui.tutorial.dao.model.Account;
import org.mapstruct.Mapper;

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
}
