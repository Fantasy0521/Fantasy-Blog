package com.fantasy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fantasy.entity.Friend;
import com.fantasy.entity.SiteSetting;
import com.fantasy.mapper.FriendMapper;
import com.fantasy.mapper.SiteSettingMapper;
import com.fantasy.model.vo.FriendInfo;
import com.fantasy.service.IFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

    @Autowired
    private SiteSettingServiceImpl siteSettingService;

    @Override
    public FriendInfo getFriendInfo(Boolean md) {
        LambdaQueryWrapper<SiteSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SiteSetting::getType,4);
        List<SiteSetting> siteSettings = siteSettingService.list(queryWrapper);
        FriendInfo friendInfo = new FriendInfo();
        for (SiteSetting siteSetting : siteSettings) {
            if ("friendContent".equals(siteSetting.getNameEn())) {
                if (md) {
                    friendInfo.setContent(MarkdownUtils.markdownToHtmlExtensions(siteSetting.getValue()));
                } else {
                    friendInfo.setContent(siteSetting.getValue());
                }
            } else if ("friendCommentEnabled".equals(siteSetting.getNameEn())) {
                if ("1".equals(siteSetting.getValue())) {
                    friendInfo.setCommentEnabled(true);
                } else {
                    friendInfo.setCommentEnabled(false);
                }
            }
        }
        return friendInfo;
    }
}
