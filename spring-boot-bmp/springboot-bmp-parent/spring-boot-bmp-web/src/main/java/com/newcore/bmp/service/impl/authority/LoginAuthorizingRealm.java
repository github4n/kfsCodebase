package com.newcore.bmp.service.impl.authority;

import com.newcore.supports.support.ClassicAuthorizingRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.halo.core.common.SpringContextHolder;
import com.halo.core.common.util.PropertiesUtils;
import com.halo.core.exception.BusinessException;
import com.halo.core.header.HeaderInfoHolder;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.service.api.authority.ClerkService;
import com.newcore.bmp.authority.util.DesUtils;
import com.newcore.supports.models.cxf.CxfHeader;
import com.newcore.supports.util.HeaderInfoUtils;

@Service("loginAuthorizingRealm")
public class LoginAuthorizingRealm extends ClassicAuthorizingRealm {

    @Override
    protected SimpleAuthenticationInfo authenticateToken(UsernamePasswordToken token) throws AuthenticationException {

    	CxfHeader headerInfo = HeaderInfoUtils.buildDefaultHeaderInfo();
        HeaderInfoHolder.setOutboundHeader(headerInfo);

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        
        ClerkService clerkService = SpringContextHolder.getBean(ClerkService.class);

        Clerk clerk = clerkService.findOne(userName);

        // 没找到帐号
        if (null == clerk) {
            throw new AuthenticationException(new BusinessException("10001","操作员账号不存在！"));
        }
        CxfHeader headerInfoPassword = HeaderInfoUtils.buildDefaultHeaderInfo();
        HeaderInfoHolder.setOutboundHeader(headerInfoPassword);
        String newpassword = clerkService.encryptPassword(DesUtils.strDec(password, null), clerk.getUserName() + clerk.getPasswordSalt());
        
        System.err.println(newpassword);
        // 帐号锁定
        if ("Y".equals(clerk.getLockFlag())) {
            throw new AuthenticationException(new BusinessException("10002","操作员账号已锁定！"));
        }
        
        // 账号无效
        if ("0".equals(clerk.getClerkStatus())) {
            throw new AuthenticationException(new BusinessException("10003","操作员状态无效！"));
        }
        
        String maxLoginFailNumStr = PropertiesUtils.getProperty("web.auth.login.fail.num");
        if(null == maxLoginFailNumStr || "".equals(maxLoginFailNumStr)){
            if (!newpassword.equals(clerk.getPassword())) {
                throw new AuthenticationException(new BusinessException("10004","操作员账号密码不匹配！"));
            }
        }else {
            Integer loginFailNum = 0;
            if(clerk.getLoginFailNum() != null){
                loginFailNum = clerk.getLoginFailNum();
            }
            Integer maxLoginFailNum = Integer.valueOf(maxLoginFailNumStr);
            /* 操作员账号密码不匹配 */
            if (!newpassword.equals(clerk.getPassword())) {
                CxfHeader loginFailNumHeaderInfo = HeaderInfoUtils.buildDefaultHeaderInfo();
                HeaderInfoHolder.setOutboundHeader(loginFailNumHeaderInfo);
                loginFailNum += 1;
                if(loginFailNum >= maxLoginFailNum){
                    clerkService.updateLionFailNum(clerk.getUserName(), loginFailNum, "Y");
                }else {
                    clerkService.updateLionFailNum(clerk.getUserName(), loginFailNum, "N");
                }
                if(maxLoginFailNum-loginFailNum == 0){
                    throw new AuthenticationException(new BusinessException("10004","操作员账号已锁定！")); 
                }else if(loginFailNum >= 2){
                    throw new AuthenticationException(new BusinessException("10004","操作员账号密码不匹配,还有"+(maxLoginFailNum-loginFailNum)+"次机会！"));
                }else{
                    throw new AuthenticationException(new BusinessException("10004","操作员账号密码不匹配！"));
                }
                
            }
            if(loginFailNum != 0){
                CxfHeader loginFailNumHeaderInfo = HeaderInfoUtils.buildDefaultHeaderInfo();
                HeaderInfoHolder.setOutboundHeader(loginFailNumHeaderInfo);
                clerkService.updateLionFailNum(clerk.getUserName(), 0, "N");
            }
        }
        
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(clerk.getUserName(), // 用户名
                password, // 密码
                ByteSource.Util.bytes(clerk.getUserName()), // salt=username+salt
                this.getRealmBeanName() // realm name
        );

        return authenticationInfo;
    }

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	
        String clerkNo = (String) principals.getPrimaryPrincipal();
        CxfHeader headerInfo = HeaderInfoUtils.buildDefaultHeaderInfo();
        HeaderInfoHolder.setOutboundHeader(headerInfo);
        ClerkService clerkService = SpringContextHolder.getBean(ClerkService.class);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(clerkService.findRoles(clerkNo));
        HeaderInfoHolder.setOutboundHeader(headerInfo);
//        authorizationInfo.setStringPermissions(clerkService.findPermissions(clerkNo));
        return authorizationInfo;
    }

}
