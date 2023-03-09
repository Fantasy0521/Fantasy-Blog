package com.fantasy.service;

import com.fantasy.entity.VisitRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface IVisitRecordService extends IService<VisitRecord> {

    Map<String, List> getVisitRecordMap();
    
}
