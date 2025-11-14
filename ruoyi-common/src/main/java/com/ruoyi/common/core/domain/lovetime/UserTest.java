package com.ruoyi.common.core.domain.lovetime;

public class UserTest {
    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setOpenid("test_openid");
        user.setInviteCode("ABC123");
        user.setNickName("Test User");
        
        System.out.println("User created successfully:");
        System.out.println("ID: " + user.getId());
        System.out.println("OpenID: " + user.getOpenid());
        System.out.println("Invite Code: " + user.getInviteCode());
        System.out.println("Nickname: " + user.getNickName());
    }
}