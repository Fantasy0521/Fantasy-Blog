package com.fantasy.service;

import com.fantasy.entity.SiteSetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface ISiteSettingService extends IService<SiteSetting> {

    Map<String, Object> getSiteInfo();

}
