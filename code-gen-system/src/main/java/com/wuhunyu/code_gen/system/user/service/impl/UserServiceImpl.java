package com.wuhunyu.code_gen.system.user.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.wuhunyu.code_gen.common.sequence.SequenceInstance;
import com.wuhunyu.code_gen.common.utils.Assert;
import com.wuhunyu.code_gen.common.utils.JwtUtil;
import com.wuhunyu.code_gen.system.listener.UserCreateListener;
import com.wuhunyu.code_gen.system.user.domain.User;
import com.wuhunyu.code_gen.system.user.domain.dto.UserDto;
import com.wuhunyu.code_gen.system.user.domain.vo.UserVo;
import com.wuhunyu.code_gen.system.user.repository.UserRepository;
import com.wuhunyu.code_gen.system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户信息处理 实现
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 16:10
 */

@Service("userService")
@RequiredArgsConstructor
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ApplicationEventPublisher publisher;

    @Override
    public String login(UserDto userDto) {
        // 查询 loginName 是否已存在
        User user = userRepository.findUserByLoginName(userDto.getLoginName());
        Assert.isTrue(user == null, "用户不存在");

        // 比对密码
        String hexPassword = DigestUtil.sha256Hex(userDto.getPassword());
        Assert.isTrue(!Objects.equals(hexPassword, user.getPassword()), "密码不正确");

        // 返回登录凭证
        return JwtUtil.createToken(user.getUserId());
    }

    @Override
    public String register(UserDto userDto) {
        // 查询 loginName 是否存在
        User user = userRepository.findUserByLoginName(userDto.getLoginName());
        Assert.isTrue(user != null, "用户已存在");

        // 生成 userId
        long userId = SequenceInstance.INSTANCE.nextId();

        // 加密密码
        String hexPassword = DigestUtil.sha256Hex(userDto.getPassword());

        // 构建 User 对象
        user = User.builder()
                .userId(userId)
                .loginName(userDto.getLoginName())
                .nickName(userDto.getNickName())
                .password(hexPassword)
                .build();
        // 新增用户
        userRepository.insertUser(user);
        // 返回登录凭证
        String token = JwtUtil.createToken(userId);

        // 异步通知初始化 用户信息
        publisher.publishEvent(new UserCreateListener.UserCreateDto(userId));

        return token;
    }

    @Override
    public void updateUser(UserDto userDto) {
        // 查询用户是否存在
        User user = userRepository.findUserByUserId(userDto.getUserId());
        Assert.isTrue(user == null, "用户不存在");

        // 复制属性
        if (StringUtils.isBlank(userDto.getNickName()) && StringUtils.isBlank(userDto.getPassword())) {
            return;
        }
        // 昵称
        if (StringUtils.isNoneBlank(userDto.getNickName()) && Objects.equals(user.getNickName(), userDto.getNickName())) {
            user.setNickName(userDto.getNickName());
        }
        // 密码
        if (StringUtils.isNoneBlank(userDto.getPassword())) {
            String hexPassword = DigestUtil.sha256Hex(userDto.getPassword());
            if (!Objects.equals(hexPassword, user.getPassword())) {
                user.setPassword(hexPassword);
            }
        }
        // 执行修改
        userRepository.updateUser(user);
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        // 获取当前用户id
        userRepository.deleteUserByUserId(userId);
    }

    @Override
    public UserVo findUserVoByUserId(Long userId) {
        User user = userRepository.findUserByUserId(userId);
        return User.convertUserToUserVo(user);
    }

    @Override
    public boolean existsUserByUserId(Long userId) {
        return userId != null &&
                userRepository.findUserByUserId(userId) != null;
    }
}
