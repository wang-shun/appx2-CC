package com.dreawer.customer.service;

import com.dreawer.customer.domain.PointRecord;
import com.dreawer.customer.lang.record.Source;
import com.dreawer.customer.lang.record.Type;
import com.dreawer.customer.persistence.PointRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <CODE>PointRecordService</CODE>
 * @author fenrir
 * @Date 18-4-13
 */
@Service
public class PointRecordService extends BaseService{

    @Autowired
    private PointRecordMapper pointRecordMapper;


    public int addPointRecord(PointRecord pointRecord) {
        int insert = pointRecordMapper.insert(pointRecord);
        return insert;
    }

    public int recordQueryCount(String storeId, String customerId, Source source, Type type) {
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        map.put("customerId",customerId);
        map.put("source",source);
        map.put("type",type);
        return pointRecordMapper.recordQueryCount(map);
    }

    public List<PointRecord> recordQuery(String storeId,
                                         String customerId,
                                         Integer pageNo,
                                         Integer pageSize,
                                         Source source,
                                         Type type) {
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        map.put("customerId",customerId);
        map.put("source",source);
        map.put("type",type);
        int start = (pageNo > 0 ? pageNo - 1 : 0) * pageSize;
        map.put("start",start);
        map.put("pageSize",pageSize);
        return pointRecordMapper.recordQuery(map);
    }
}
