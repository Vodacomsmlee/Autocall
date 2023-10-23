package com.vdc.autocall.service;


import com.vdc.autocall.dao.DeviceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("deviceService")
public class DeviceService {
    @Resource(name="deviceDao")
    private DeviceDao deviceDao;

    public List<Map<String, Object>> DeviceList(Map<String, Object> map) throws Exception {
        return deviceDao.DeviceList(map);
    }
    public void addDevice(Map<String, Object> map) throws Exception {
        deviceDao.AddDevice(map);
    }
    public Object editDevice(Map<String, Object> map) throws Exception {
        return deviceDao.EditDevice(map);
    }
    public void delDevice(Map<String, Object> map) throws Exception {
        deviceDao.DelDevice(map);
    }

}
