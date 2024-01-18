package com.vdc.autocall.dao;

import com.vdc.autocall.common.dao.AbstractDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;


@Repository("accountDao")
public class AccountDao extends AbstractDAO{
    @Resource(name="sqlSessionMain")
    private SqlSessionTemplate sqlSession;

    public int account_add(Map<String, Object> map) {
        return (int) insert(sqlSession, "Account.account_add", map);
    }
    public Map<String, Object> account_login(Map<String, Object> map){
        return (Map<String, Object>)selectOne(sqlSession, "Account.account_login", map);
    }
}
