package com.fantasy.friend;

import com.fantasy.controller.FriendController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FriendControllerTest {

    @Autowired
    private FriendController friendController;

    @Test
    void testFriends(){
        friendController.friends();
    }

}
