package com.fantasy.service.impl;

import com.fantasy.entity.SiteSetting;
import com.fantasy.mapper.SiteSettingMapper;
import com.fantasy.model.bean.Badge;
import com.fantasy.model.bean.Copyright;
import com.fantasy.model.bean.Favorite;
import com.fantasy.model.bean.Introduction;
import com.fantasy.service.ISiteSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.JacksonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Service
public class SiteSettingServiceImpl extends ServiceImpl<SiteSettingMapper, SiteSetting> implements ISiteSettingService {

    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");

    /**
     * 获取所有站点信息
     * 太多了直接copy
     * @return
     */
    @Override
    public Map<String, Object> getSiteInfo() {
        List<SiteSetting> siteSettings = this.list();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> siteInfo = new HashMap<>();
        List<Badge> badges = new ArrayList<>();
        Introduction introduction = new Introduction();
        List<Favorite> favorites = new ArrayList<>();
        List<String> rollTexts = new ArrayList<>();
        for (SiteSetting s : siteSettings) {
            if (s.getType() == 1) {
                if ("copyright".equals(s.getNameEn())) {
                    Copyright copyright = JacksonUtils.readValue(s.getValue(), Copyright.class);
                    siteInfo.put(s.getNameEn(), copyright);
                } else {
                    siteInfo.put(s.getNameEn(), s.getValue());
                }
            } else if (s.getType() == 2) {
                Badge badge = JacksonUtils.readValue(s.getValue(), Badge.class);
                badges.add(badge);
            } else if (s.getType() == 3) {
                if ("avatar".equals(s.getNameEn())) {
                    introduction.setAvatar(s.getValue());
                } else if ("name".equals(s.getNameEn())) {
                    introduction.setName(s.getValue());
                } else if ("github".equals(s.getNameEn())) {
                    introduction.setGithub(s.getValue());
                } else if ("qq".equals(s.getNameEn())) {
                    introduction.setQq(s.getValue());
                } else if ("bilibili".equals(s.getNameEn())) {
                    introduction.setBilibili(s.getValue());
                } else if ("netease".equals(s.getNameEn())) {
                    introduction.setNetease(s.getValue());
                } else if ("email".equals(s.getNameEn())) {
                    introduction.setEmail(s.getValue());
                } else if ("favorite".equals(s.getNameEn())) {
                    Favorite favorite = JacksonUtils.readValue(s.getValue(), Favorite.class);
                    favorites.add(favorite);
                } else if ("rollText".equals(s.getNameEn())) {
                    Matcher m = PATTERN.matcher(s.getValue());
                    while (m.find()) {
                        rollTexts.add(m.group(1));
                    }
                }
            }
        }
        introduction.setFavorites(favorites);
        introduction.setRollText(rollTexts);
        map.put("introduction", introduction);
        map.put("siteInfo", siteInfo);
        map.put("badges", badges);
        return map;
    }
}
