package com.fantasy.Comment;

import com.fantasy.service.impl.CommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    void testGetCommentsList(){
        commentService.getCommentsList(0,3L,1,10);
    }

}
