package com.fantasy.Blog;

import com.fantasy.controller.BlogController;
import com.fantasy.model.Result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class BlogControllerTest {

    @Resource
    private BlogController blogController;

    @Test
    void testGetAllBlogs(){
        Result allBlogs = blogController.getAllBlogs(1);
        System.out.println(allBlogs);
    }

    @Test
    void testGetOneBlogsById(){
        Result oneBlogsById = blogController.getOneBlogsById(1L);
        System.out.println(oneBlogsById);
    }

    @Test
    void testCategory(){
        Result result = blogController.category("动漫", 1);
        System.out.println(result);
    }

}
