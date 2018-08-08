package com.dreawer.customer.persistence;

import com.dreawer.customer.domain.PointRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <CODE>PointRecordMapper</CODE>
 * @author fenrir
 * @Date 18-4-13
 */
@Repository
public interface PointRecordMapper {

    int insert(PointRecord pointRecord);

    int recordQueryCount(Map<String, Object> map);

    List<PointRecord> recordQuery(Map<String, Object> map);
}
