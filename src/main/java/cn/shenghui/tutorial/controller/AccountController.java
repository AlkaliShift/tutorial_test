package cn.shenghui.tutorial.controller;

import cn.shenghui.tutorial.dao.model.Account;
import cn.shenghui.tutorial.rest.request.CreateAccountRequest;
import cn.shenghui.tutorial.rest.response.CreateAccountResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author: shenghui
 * @Date: {2019/7/26} {14:17}
 * account controller/uri
 */
@RestController
@Api(tags = "Account")
public class AccountController {
    @ApiOperation(value = "create account",
            notes = "statusCode = 1, success and return account id; statusCode = 0, failed.")
    @PostMapping("/createAccount")
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest){
        Account account = new Account();
        account.setAccountName(createAccountRequest.getAccountName());
        account.setPayPassword(createAccountRequest.getAccountPassword());
        account.setBalance(createAccountRequest.getBalance());
        CreateAccountResponse response = new CreateAccountResponse();
        response.setAccountId(account.getAccountId());
        return response;
    }
}
