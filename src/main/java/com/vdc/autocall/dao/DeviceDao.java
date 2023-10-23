package com.vdc.autocall.dao;


import com.vdc.autocall.common.dao.AbstractDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("deviceDao")
public class DeviceDao extends AbstractDAO {
    @Resource(name="sqlSessionMain")
    private SqlSessionTemplate sqlSession;

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> DeviceList(Map<String, Object> map) {
        return (List<Map<String, Object>>)selectList(sqlSession, "Device.DeviceList", map);
    }
    public void AddDevice(Map<String, Object> map) {
        insert(sqlSession, "Device.DeviceAdd", map);
    }

    public Object EditDevice(Map<String, Object> map) {
        return update(sqlSession, "Device.DeviceEdit", map);
    }

    public void DelDevice(Map<String, Object> map) {
        delete(sqlSession, "Device.DeviceDel", map);
    }
}

