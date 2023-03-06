package com.fantasy.service;

import com.fantasy.entity.Moment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasy.model.Result.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface IMomentService extends IService<Moment> {

    PageResult<Moment> getMomentsByPage(Integer pageNum);
}
