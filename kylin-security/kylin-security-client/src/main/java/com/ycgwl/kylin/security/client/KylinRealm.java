package com.ycgwl.kylin.security.client;

import com.ycgwl.kylin.security.entity.User;
import com.ycgwl.kylin.security.service.api.SysUserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KylinRealm extends AuthorizingRealm {


  private static Logger log = LoggerFactory.getLogger(KylinRealm.class);

  private SysUserService userService;

  public SysUserService getUserService() {
    return userService;
  }

  public void setUserService(SysUserService userService) {
    this.userService = userService;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    //用户登录后，获取用户授权信息
    //null usernames are invalid
    if (principals == null) {
      throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
    }
    User user = (User) getAvailablePrincipal(principals);
    Set<String> roleNames = null;
    Set<String> permissions = new HashSet<>();
    Set<String> permsSet = new HashSet<String>();
    try {
      List<String> permissionList = userService.queryAllPerms(user.getAccount());
          /*  permissionList.forEach(e -> {
                if (StringUtils.isNotEmpty(e)) {
                    String[] permissionArray = e.split(",");
                    for (String p : permissionArray) {
                        permissions.add(p);
                   }
                }
            });*/

      //用户权限列表

      for (String perms : permissionList) {
        if (StringUtils.isBlank(perms)) {
          continue;
        }
        permsSet.addAll(Arrays.asList(perms.trim().split(",")));

      }

      //roleNames = Collections.emptySet();
    } catch (Exception e) {
      final String message = "There was a error while authorizing user [" + user.getAccount() + "]";
      if (log.isErrorEnabled()) {
        log.error(message, e);
      }
      // Rethrow any SQL errors as an authorization exception
      throw new AuthorizationException(message, e);
    }

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    // info.setStringPermissions(permissions);
    info.setStringPermissions(permsSet);
    return info;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    //认证用户，并生成授权信息
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String username = upToken.getUsername();
    // Null username is invalid
    if (username == null) {
      throw new AccountException("Null usernames are not allowed by this realm.");
    }
    SimpleAuthenticationInfo info = null;
    try {
      User user = userService.queryByUserName(username);
      if (user == null) {
        throw new UnknownAccountException("No account found for user [" + username + "]");
      }
      info = new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), getName());
    } catch (Exception e) {
      if (!(e instanceof UnknownAccountException)) {
        final String message = "There was a  error while authenticating user [" + username + "]";
        if (log.isErrorEnabled()) {
          log.error(message, e);
        }
        e.printStackTrace();
        // Rethrow any SQL errors as an authentication exception
        throw new AuthenticationException(message, e);

      } else {
        throw e;
      }
    }
    return info;
  }

  @Override
  public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {

    HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
    shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
    shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
    super.setCredentialsMatcher(shaCredentialsMatcher);

  }

}
