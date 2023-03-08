package com.fantasy.service.impl;

import com.fantasy.entity.ExceptionLog;
import com.fantasy.exception.BizException;
import com.fantasy.mapper.ExceptionLogMapper;
import com.fantasy.service.IExceptionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.IpAddressUtils;
import com.fantasy.util.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements IExceptionLogService {

    @Autowired
    UserAgentUtils userAgentUtils;
    
    /**
     * 异步记录异常信息
     * @param log
     */
    @Override
    @Transactional
    public void saveExceptionLog(ExceptionLog log) {
        // 从UserAgent中获取操作系统,浏览器等信息
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        Map<String,String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        log.setIpSource(ipSource);
        log.setBrowser(browser);
        log.setOs(os);
        if (!this.save(log)) {
            throw new RuntimeException(new BizException("日志添加失败"));
        }
    }
    
}
