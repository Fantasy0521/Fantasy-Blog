package com.fantasy.archive;

import com.fantasy.service.impl.BlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArchiveImplTest {

    @Autowired
    private BlogServiceImpl blogService;

    @Test
    void testGetArchiveBlogAndCountByIsPublished(){
        blogService.getArchiveBlogAndCountByIsPublished();
    }

}
