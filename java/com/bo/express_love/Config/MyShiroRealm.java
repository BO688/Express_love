package com.bo.express_love.Config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        System.out.println(principals.getRealmNames());//放行,不配置权限
//        authorizationInfo.addRole("admin");

//        for(SysRole role:userInfo.getRoleList()){
//            authorizationInfo.addRole(role.getRole());
//            for(SysPermission p:role.getPermissions()){
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        System.out.println(username);
//        System.out.println(((UsernamePasswordToken) token ).getPassword());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        UserInfo userInfo = userInfoService.findByUsername(username);
        if(username==null){
            throw new UnknownAccountException();
        }
        System.out.println(token.getCredentials().toString());
        System.out.println("----->>userInfo="+username);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                token.getCredentials(), //密码 token.getCredentials()使用原密码，不进行验证
//                ByteSource.Util.bytes(userInfo.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}

