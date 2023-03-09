package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fantasy.entity.Visitor;
import com.fantasy.exception.BizException;
import com.fantasy.mapper.VisitorMapper;
import com.fantasy.service.IVisitorService;
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
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements IVisitorService {

    @Autowired
    private UserAgentUtils userAgentUtils;
    
    /**
     * 判断是否存在该uuid
     * @param identification
     * @return
     */
    @Override
    public boolean hasUUID(String identification) {
        LambdaQueryWrapper<Visitor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Visitor::getUuid,identification);
        List<Visitor> list = this.list();
        if (list == null || list.size() <= 0){
            return false;
        }else {
            return true;            
        }
    }

    /**
     * 保存
     * @param visitor
     */
    @Override
    @Transactional
    public void saveVisitor(Visitor visitor) {
        String ipSource = IpAddressUtils.getCityInfo(visitor.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(visitor.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        visitor.setIpSource(ipSource);
        visitor.setOs(os);
        visitor.setBrowser(browser);
        //存入数据库
        if (!this.save(visitor)) {
            throw new RuntimeException(new BizException("访客添加失败"));
        }
        
    }
}
