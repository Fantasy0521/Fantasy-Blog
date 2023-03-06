package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fantasy.entity.Moment;
import com.fantasy.mapper.MomentMapper;
import com.fantasy.model.Result.PageResult;
import com.fantasy.service.IMomentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.markdown.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class MomentServiceImpl extends ServiceImpl<MomentMapper, Moment> implements IMomentService {

    @Override
    public PageResult<Moment> getMomentsByPage(Integer pageNum) {
        PageHelper.startPage(pageNum,3);
        LambdaQueryWrapper<Moment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Moment::getIsPublished,true).orderByDesc(Moment::getCreateTime);
        List<Moment> list = this.list();
        for (Moment moment : list) {
            moment.setContent(MarkdownUtils.markdownToHtmlExtensions(moment.getContent()));
        }
        PageInfo<Moment> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getPages(),pageInfo.getList());
    }

}
