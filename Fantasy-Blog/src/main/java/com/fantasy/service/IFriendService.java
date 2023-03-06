package com.fantasy.service;

import com.fantasy.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasy.model.vo.FriendInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
public interface IFriendService extends IService<Friend> {

    FriendInfo getFriendInfo(Boolean md);
}
