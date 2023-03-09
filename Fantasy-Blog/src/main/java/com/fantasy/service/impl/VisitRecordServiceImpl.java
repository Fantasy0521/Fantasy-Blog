package com.fantasy.service.impl;

import com.fantasy.entity.VisitRecord;
import com.fantasy.mapper.VisitRecordMapper;
import com.fantasy.service.IVisitRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Service
public class VisitRecordServiceImpl extends ServiceImpl<VisitRecordMapper, VisitRecord> implements IVisitRecordService {

    @Autowired
    private VisitRecordMapper visitRecordMapper;

    //查询最近30天的记录
    private static final int visitRecordLimitNum = 30;
    
    @Override
    public Map<String, List> getVisitRecordMap() {
        List<VisitRecord> visitRecordList = visitRecordMapper.getVisitRecordListByLimit(visitRecordLimitNum);
        List<String> date = new ArrayList<>(visitRecordList.size());
        List<Integer> pv = new ArrayList<>(visitRecordList.size());
        List<Integer> uv = new ArrayList<>(visitRecordList.size());
        for (int i = visitRecordList.size() - 1; i >= 0; i--) {
            VisitRecord visitRecord = visitRecordList.get(i);
            date.add(visitRecord.getDate());
            pv.add(visitRecord.getPv());
            uv.add(visitRecord.getUv());
        }
        Map<String, List> map = new HashMap<>();
        map.put("date", date);
        map.put("pv", pv);
        map.put("uv", uv);
        return map;
    }
}
