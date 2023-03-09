package com.fantasy.mapper;

import com.fantasy.entity.VisitLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Mapper
public interface VisitLogMapper extends BaseMapper<VisitLog> {

    int countVisitLogByToday();

    List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate);
}
