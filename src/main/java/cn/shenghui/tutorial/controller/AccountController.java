package cn.shenghui.tutorial.controller;

import cn.shenghui.tutorial.dao.model.Account;
import cn.shenghui.tutorial.rest.request.CreateAccountRequest;
import cn.shenghui.tutorial.rest.request.PayRequest;
import cn.shenghui.tutorial.rest.request.UpdateAccountRequest;
import cn.shenghui.tutorial.rest.response.*;
import cn.shenghui.tutorial.service.AccountService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author shenghui
 * @version 1.0
 * @since 2019/7/26 14:17
 * account controller/uri
 */
@Controller
@Api(tags = "Account")
public class AccountController {
    private static final String PATH = "D:/image";
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * get account list
     * @return mv
     */
    @RequestMapping("/list")
    public ModelAndView getAccountList() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("account");
        return mv;
    }

    /**
     * add account page
     * @return mv
     */
    @RequestMapping("/add")
    public ModelAndView addAccount() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("accountAdd");
        return mv;
    }

    /**
     * edit account page
     * @param accountId account id
     * @return mv
     */
    @RequestMapping("/edit")
    public ModelAndView editAccount(@RequestParam(name = "accountId") String accountId) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("accountEdit");
        Account account = accountService.getAccountInfo(accountId);
        mv.addObject("account", account);
        return mv;
    }

    /**
     * upload image page
     * @param accountId account id
     * @return mv
     */
    @RequestMapping("/upload")
    public ModelAndView uploadPage(@RequestParam(name = "accountId") String accountId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("accountId", accountId);
        mv.setViewName("upload");
        return mv;
    }

    /**
     * image captcha
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception e
     */
    @RequestMapping("/images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(130, 48, 5, request, response);
    }

    @ApiOperation(value = "create account",
            notes = "statusCode = 1, success and return account id; statusCode = 0, failed.")
    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public CreateAccountResponse createAccount(@RequestBody @Validated CreateAccountRequest createAccountRequest,
                                               HttpServletRequest request) {
        CreateAccountResponse response = new CreateAccountResponse();
        String captcha = createAccountRequest.getCaptcha();
        String sessionCode = request.getSession().getAttribute("captcha").toString();
        if (captcha == null || !sessionCode.equals(captcha.trim().toLowerCase())) {
            response.setStatusInfo(0, "Captcha error.");
        }else{
            Account account = new Account();
            account.setAccountName(createAccountRequest.getAccountName());
            account.setPayPassword(createAccountRequest.getAccountPassword());
            String accountId = accountService.createAccount(account);
            response.setAccountId(accountId);
            response.setStatusCode(1);
        }
        return response;
    }

    @ApiOperation(value = "get account information",
            notes = "statusCode = 0, failed; " +
                    "statusCode = 1, success and return account information; " +
                    "statusCode = 2, id does not exist.")
    @RequestMapping(value = "/getAccountInfo", method = RequestMethod.GET)
    @ResponseBody
    public AccountListResponse getAccountInfo(@RequestParam(name = "accountId") String accountId,
                                              @RequestParam(name = "page") int page,
                                              @RequestParam(name = "limit") int limit) {
        AccountListResponse response = new AccountListResponse();
        PageInfo<Account> pages = accountService.searchAccountPageInfo(accountId, page, limit);
        if (ObjectUtils.isEmpty(pages.getList())) {
            response.setStatusInfo(0, "ID does not exist.");
        } else {
            response.setAccountList(pages.getList());
            response.setTotal(pages.getTotal());
            response.setStatusCode(1);
        }
        return response;
    }

    @ApiOperation(value = "pay", notes = "statusCode = 0, failed; " +
            "statusCode = 1, success and return balance; " +
            "statusCode = 2, id does not exist.")
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AccountResponse pay(@RequestBody PayRequest payRequest) {
        AccountResponse response = new AccountResponse();
        if (payRequest.getAccountId().isEmpty() || payRequest.getAmount() == 0) {
            response.setStatusInfo(0, "Incomplete request.");
        } else {
            String accountId = payRequest.getAccountId();
            Account account = accountService.getAccountInfo(accountId);
            if (ObjectUtils.isEmpty(account)) {
                response.setStatusInfo(0, "ID does not exist.");
            } else {
                long amount = payRequest.getAmount();
                long balance = account.getBalance();
                if (amount > balance) {
                    response.setStatusInfo(0, "Insufficient account balance.");
                } else {
                    accountService.pay(accountId, amount);
                    balance = balance - amount;
                    response.setAccountId(accountId);
                    response.setAccountName(account.getAccountName());
                    response.setBalance(balance);
                    response.setStatusCode(1);
                }
            }
        }
        return response;
    }

    @ApiOperation(value = "update account", notes = "statusCode = 0, failed; " +
            "statusCode = 1, success")
    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    @ResponseBody
    public BasicAccountResponse updateAccount(@RequestBody @Validated UpdateAccountRequest updateAccountRequest) {
        BasicAccountResponse response = new BasicAccountResponse();
        Account account = new Account();
        account.setAccountId(updateAccountRequest.getAccountId());
        account.setAccountName(updateAccountRequest.getAccountName());
        account.setPayPassword(updateAccountRequest.getAccountPassword());
        accountService.updateAccount(account);
        response.setStatusInfo(1, "Update success");
        return response;
    }

    @ApiOperation(value = "delete account", notes = "statusCode = 0, failed; " +
            "statusCode = 1, success")
    @RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
    @ResponseBody
    public BasicAccountResponse deleteAccount(@RequestParam(name = "accountId") String accountId) {
        BasicAccountResponse response = new BasicAccountResponse();
        accountService.deleteAccount(accountId);
        response.setStatusInfo(1, "Delete success");
        return response;
    }

    @ApiOperation(value = "upload image", notes = "statusCode = 0, failed; " +
            "statusCode = 1, success")
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public BasicAccountResponse uploadImage(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id") String accountId) {
        BasicAccountResponse response = new BasicAccountResponse();
        if (file.isEmpty()) {
            response.setStatusInfo(0, "Please upload one image.");
        } else {
            String filename = accountId + "_" + file.getOriginalFilename();
            String pathname = PATH + "/" + filename;
            File dest = new File(pathname);
            InputStream in = null;
            FileOutputStream out = null;

            //保存文件
            try {
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                if (!dest.exists()){
                    dest.createNewFile();
                }
                in = file.getInputStream();
                out = new FileOutputStream(dest);
                byte[] b = new byte[1024];
                int length;
                while ((length = in.read(b)) > 0) {
                    out.write(b, 0, length);
                }
                accountService.updateAccountPath(accountId, pathname);
                response.setStatusInfo(1, "Upload success.");
                out.flush();
            } catch (Exception e) {
                response.setStatusInfo(2, "Upload failed: " + e.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    @ApiOperation(value = "download image", notes = "statusCode = 0, failed; " +
            "statusCode = 1, success")
    @RequestMapping(value = "/downloadImage")
    public void downloadImage(@RequestParam(name = "accountId") String accountId, HttpServletResponse httpServletResponse) {
        List<Account> list = accountService.searchAccountInfo(accountId);
        String path = list.get(0).getPath();
        File file = new File(path);
        InputStream in = null;
        OutputStream out = null;
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                out = httpServletResponse.getOutputStream();
                byte[] b = new byte[1024];
                int length;
                while ((length = in.read(b)) > 0) {
                    out.write(b, 0, length);
                }
                httpServletResponse.setContentType("image/jpg");
                out.flush();
            } catch (Exception e) {
                e.getMessage();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ioe) {
                        ioe.getMessage();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ioe) {
                        ioe.getMessage();
                    }
                }
            }
        }
    }
}
