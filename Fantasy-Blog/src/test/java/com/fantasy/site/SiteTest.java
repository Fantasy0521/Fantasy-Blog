package com.fantasy.site;

import com.fantasy.service.impl.SiteSettingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class SiteTest {

    @Autowired
    private SiteSettingServiceImpl siteSettingService;

    @Test
    void testGetSiteInfo(){
        Map<String, Object> siteInfo = siteSettingService.getSiteInfo();
        System.out.println(siteInfo);
    }

}
