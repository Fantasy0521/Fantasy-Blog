package com.fantasy.service.impl;

import com.fantasy.entity.About;
import com.fantasy.mapper.AboutMapper;
import com.fantasy.service.IAboutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasy.util.markdown.MarkdownUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements IAboutService {

    @Override
    public Map<String, String> getAbout() {
        List<About> list = this.list();
        Map<String, String> aboutInfoMap = new HashMap<>();
        for (About about : list) {
            if ("content".equals(about.getNameEn())) {
                about.setValue(MarkdownUtils.markdownToHtmlExtensions(about.getValue()));
            }
            aboutInfoMap.put(about.getNameEn(), about.getValue());
        }
        return aboutInfoMap;
    }
}
