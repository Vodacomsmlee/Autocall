package com.vdc.autocall.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AbstractDAO {
    private final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);


    protected void printQueryId(String queryId) {
        if(logger.isDebugEnabled()){
            logger.debug("\t QueryId  \t:  " + queryId);
        }
    }

    public Object insert(SqlSessionTemplate sqlSession, String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }

    public Object update(SqlSessionTemplate sqlSession, String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }

    public Object delete(SqlSessionTemplate sqlSession, String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }

    public Object selectOne(SqlSessionTemplate sqlSession, String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }

    public Object selectOne(SqlSessionTemplate sqlSession, String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }


    @SuppressWarnings("rawtypes")
    public List selectList(SqlSessionTemplate sqlSession, String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }
}
