package com.wuhunyu.code_gen.system.user.service;

import com.wuhunyu.code_gen.system.user.domain.dto.UserDto;
import com.wuhunyu.code_gen.system.user.domain.vo.UserVo;

/**
 * 用户信息处理
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 16:09
 */

public interface UserService {

    /**
     * 登录
     *
     * @param userDto 用户信息
     * @return 访问凭证
     */
    String login(UserDto userDto);

    /**
     * 注册
     *
     * @param userDto 用户信息
     * @return 访问凭证
     */
    String register(UserDto userDto);

    /**
     * 修改用户昵称/密码
     *
     * @param userDto 用户信息
     */
    void updateUser(UserDto userDto);

    /**
     * 删除当前用户(完全注销)
     */
    void deleteCurUser();

    /**
     * 查询当前用户信息
     *
     * @return 当前用户信息
     */
    UserVo findUserCurVo();

}
