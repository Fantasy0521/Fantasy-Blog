package com.fantasy.mybatisplus;

import com.fantasy.entity.Blog;
import com.fantasy.service.impl.BlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class BlogTest {

    @Autowired
    private BlogServiceImpl blogService;

    @Test
    void testList(){
        List<Blog> list = blogService.list();
        System.out.println(list);
    }

}
