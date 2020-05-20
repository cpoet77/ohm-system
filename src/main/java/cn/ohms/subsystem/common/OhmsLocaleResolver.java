// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/5/1.
package cn.ohms.subsystem.common;


import cs.ohmsubsystem.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


/**
 * 区域解析
 * <p><b>i18n 国际化实现</b></p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @author _Struggler
 *
 */
public class OhmsLocaleResolver implements LocaleResolver {
    private final static Logger log = LoggerFactory.getLogger(OhmsLocaleResolver.class);

    public OhmsLocaleResolver (String defaultLocale) {
        Locale.setDefault(new Locale(defaultLocale));
    }

    @NotNull
    @Override
    public Locale resolveLocale (@NotNull HttpServletRequest httpServletRequest) {
        try {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals(UserService.LANGUAGE_SELF_COOKIE)) {
                    return (new Locale(cookie.getValue()));
                }
            }
        }catch (NullPointerException e){
            log.debug(e.getMessage());
        }

        return httpServletRequest.getLocale();
    }

    @Override
    public void setLocale (@NotNull HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , Locale locale) {
    }
}
