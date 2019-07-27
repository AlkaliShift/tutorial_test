package cn.shenghui.tutorial.controller;

import cn.shenghui.tutorial.dao.model.Account;
import cn.shenghui.tutorial.rest.request.CreateAccountRequest;
import cn.shenghui.tutorial.rest.response.AccountResponse;
import cn.shenghui.tutorial.rest.response.CreateAccountResponse;
import cn.shenghui.tutorial.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @author shenghui
 * @since 2019/7/26 14:17
 * account controller/uri
 */
@RestController
@Api(tags = "Account")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "create account",
            notes = "statusCode = 1, success and return account id; statusCode = 0, failed.")
    @PostMapping("/createAccount")
    @Transactional(rollbackFor = Exception.class)
    public CreateAccountResponse createAccount(@RequestBody @Validated CreateAccountRequest createAccountRequest){
        CreateAccountResponse response = new CreateAccountResponse();
        if (createAccountRequest.getAccountName().isEmpty() || createAccountRequest.getAccountPassword().isEmpty()){
            response.setStatusInfo(0, "Incomplete request.");
            return response;
        }else{
            Account account = new Account();
            account.setAccountName(createAccountRequest.getAccountName());
            account.setPayPassword(createAccountRequest.getAccountPassword());
            String accountId = accountService.createAccount(account);
            response.setAccountId(accountId);
            response.setStatusCode(1);
            return response;
        }
    }

    @ApiOperation(value = "get account information",
            notes = "statusCode = 1, success and return account information; statusCode = 0, failed")
    @GetMapping("/getAccountInfo")
    public AccountResponse getAccountInfo(@RequestParam(name = "accountId") String accountId){
        AccountResponse response = new AccountResponse();
        if (accountId.isEmpty()){
            response.setStatusInfo(0, "Account ID is null.");
            return response;
        }else{
            Account account = accountService.getAccountInfo(accountId);
            if(ObjectUtils.isEmpty(account)){
                response.setStatusInfo(2, "ID does not exist.");
                return response;
            }else{
                response.setAccountId(accountId);
                response.setAccountName(account.getAccountName());
                response.setBalance(account.getBalance());
                response.setStatusCode(1);
                return response;
            }
        }
    }
}
