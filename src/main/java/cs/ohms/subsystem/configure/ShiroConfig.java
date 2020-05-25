// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/3.
package cs.ohms.subsystem.configure;

import cs.ohms.subsystem.common.NSCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Shiro权限管理
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Configuration
public class ShiroConfig {

    /**
     * 代理控制
     * @param securityManager SecurityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /* 那些与权限相关的设定，h-h-h-h-h */
        shiroFilterFactoryBean.setLoginUrl("/login");
        return shiroFilterFactoryBean;
    }

    /**
     * shiro权限控制, shiro aop，开启注解支持
     * @param securityManager 安全管理
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor sourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

    /**
     * 安全管理器
     * @return SecurityManager
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(JdbcRealm jdbcRealm, SessionManager sessionManager){
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        /* 权限域 */
        securityManager.setRealm(jdbcRealm);
        /* Session管理器 */
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * 会话Cookie模板
     * @return SimpleCookie
     */
    @Bean(name = "sessionIdCookie")
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie(Base64.decodeToString("TlNsZWFm"));
        simpleCookie.setHttpOnly(true);
        // -1为仅限于当前浏览有效
        // 设置该cookie有效期为5天
        simpleCookie.setMaxAge(432000);
        return simpleCookie;
    }

    /**
     * 会话id生产
     * @return JavaUuidSessionIdGenerator
     */
    @Bean(name = "sessionIdGenerator")
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 会话dao
     * @param sessionIdGenerator 会话id生产器
     * @return EnterpriseCacheSessionDAO
     */
    @Bean(name = "sessionDao")
    public EnterpriseCacheSessionDAO cacheSessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return sessionDAO;
    }

    /**
     * 会话管理
     * @param sessionDAO 会话dao
     * @return sessionManager
     */
    @Bean(name = "sessionManager")
    public DefaultSessionManager sessionManager(SessionDAO sessionDAO, SimpleCookie sessionIdCookie){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(999999);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        return sessionManager;
    }

    /**
     * 配置数据库信息,jdbc域
     * 查询信息
     * @param dataSource 数据源
     * @return NSJdbcRealm
     */
    @Bean(name = "jdbcRealm")
    public JdbcRealm jdbcRealm(DataSource dataSource){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setName("jdbcRealm");
        String sqlSelectRole = "SELECT name role_name FROM ohms_role WHERE id IN (SELECT role_id FROM ohms_user_role our , ohms_user ou WHERE our.user_id = ou.id AND ou.name = ?)";
        String sqlSelectPermission = "SELECT name permission_name FROM ohms_role WHERE name = ?";
        String sqlSelectAuthentication = "SELECT password, salt password_salt FROM ohms_user WHERE name = ?";
        jdbcRealm.setUserRolesQuery(sqlSelectRole);
        jdbcRealm.setPermissionsQuery(sqlSelectPermission);
        jdbcRealm.setAuthenticationQuery(sqlSelectAuthentication);
        /* 启用数据库查询盐 */
        jdbcRealm.setSaltStyle(JdbcRealm.SaltStyle.COLUMN);
        jdbcRealm.setCredentialsMatcher(new NSCredentialsMatcher());
        return jdbcRealm;
    }
}
