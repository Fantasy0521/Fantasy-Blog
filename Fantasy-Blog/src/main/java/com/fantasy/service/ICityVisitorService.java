package com.fantasy.service;

import com.fantasy.entity.CityVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface ICityVisitorService extends IService<CityVisitor> {

    List<CityVisitor> getCityVisitorList();
    
}
