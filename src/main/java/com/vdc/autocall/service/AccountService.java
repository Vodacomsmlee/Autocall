package com.vdc.autocall.service;

import com.vdc.autocall.dao.AccountDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
@Service("accountService")
public class AccountService {
    @Resource(name="accountDao")
    private AccountDao accountDao;

    public int account_add(Map<String, Object> map) throws Exception{
        return accountDao.account_add(map);
    }

    public Map<String, Object> account_login(Map<String, Object> map) throws Exception {
        return accountDao.account_login(map);
    }
}
