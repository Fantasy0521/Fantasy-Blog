package com.fantasy.service.impl;

import com.fantasy.entity.VisitLog;
import com.fantasy.exception.BizException;
import com.fantasy.mapper.VisitLogMapper;
import com.fantasy.service.IVisitLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.IpAddressUtils;
import com.fantasy.util.UserAgentUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements IVisitLogService {

    @Autowired
    private UserAgentUtils userAgentUtils;
    
    @Autowired
    private VisitLogMapper visitLogMapper;
    
    @Override
    @Transactional
    public void saveVisitLog(VisitLog log) {
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        log.setIpSource(ipSource);
        log.setOs(os);
        log.setBrowser(browser);
        if (!this.save(log)) {
            throw new RuntimeException(new BizException("日志添加失败"));
        }
    }

    /**
     * 统计今天的访问人数
     * @return
     */
    @Override
    public int countVisitLogByToday() {
        return visitLogMapper.countVisitLogByToday();
    }

    @Override
    public List<VisitLog> getVisitLogListByUUIDAndDate(String uuid, String startDate, String endDate) {
        return visitLogMapper.getVisitLogListByUUIDAndDate(uuid, startDate, endDate);    }
}
