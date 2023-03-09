package com.fantasy.service.impl;

import com.fantasy.entity.CityVisitor;
import com.fantasy.mapper.CityVisitorMapper;
import com.fantasy.service.ICityVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Service
public class CityVisitorServiceImpl extends ServiceImpl<CityVisitorMapper, CityVisitor> implements ICityVisitorService {

    @Override
    public List<CityVisitor> getCityVisitorList() {
        return this.list();
    }
}
