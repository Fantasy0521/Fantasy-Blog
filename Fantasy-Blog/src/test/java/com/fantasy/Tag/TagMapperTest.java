package com.fantasy.Tag;

import com.fantasy.entity.Tag;
import com.fantasy.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    void testGetTagListByBlogId(){
        List<Tag> tagListById = tagMapper.getTagListById(3L);
        System.out.println(tagListById);
    }

}
