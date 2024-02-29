package com.fantasy.Blog;

import com.fantasy.controller.BlogController;
import com.fantasy.controller.admin.BlogAdminController;
import com.fantasy.model.Result.Result;
import com.fantasy.service.impl.BlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BlogAdminControllerTest {

    @Resource
    private BlogServiceImpl blogService;

    @Test
    void testCategory(){
        blogService.imageUploadHandler("![debug.png](1)\n\n111\n![下载debug.jfif](1)");

    }
}
