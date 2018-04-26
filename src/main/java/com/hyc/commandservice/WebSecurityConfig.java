package com.hyc.commandservice;

import com.google.common.collect.ImmutableList;
import com.hyc.commandservice.service.impl.UserServiceImpl;
import com.hyc.commandservice.utils.DigestAuthenticationEntryPoint;
import com.hyc.commandservice.utils.DigestAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userservice;

    //此处仅做权限验证,不涉及权限范围验证，如审核的机构是不是自己的子机构等
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        DigestAuthenticationEntryPoint digestAuthenticationEntry = new DigestAuthenticationEntryPoint();
        digestAuthenticationEntry.setRealmName("commandService");
        digestAuthenticationEntry.setKey("LAW");
        digestAuthenticationEntry.setNonceValiditySeconds(60);
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(digestAuthenticationEntry);
        filter.setUserDetailsService(userservice);
        filter.setPasswordAlreadyEncoded(true);
//        filter.setUserCache(userservice.getUserCache());
        http
                .cors()
                .and()
                .csrf().disable()
                .addFilterAt(filter, org.springframework.security.web.authentication.www.DigestAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(digestAuthenticationEntry)
                .and()
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).and()
                .sessionFixation().newSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/app/*/*/lasteversion", "/app/*/*/version/*/rawdata", "/", "/app/smsverifycode", "/js/**", "/css/**", "/images/**", "/lib/**", "/template/**", "/wordbook", "/logout", "/index.html**").permitAll()
                .antMatchers("/Test/*").permitAll()
                //角色
                .antMatchers(HttpMethod.GET, "/role/**", "/privileges").access("hasAuthority('role_view')")
                .antMatchers(HttpMethod.PUT, "/role").hasAuthority("role_add")
                .antMatchers(HttpMethod.PUT, "/role/*").hasAuthority("role_modify")
                //回访模板
                .antMatchers(HttpMethod.GET, "/interviewTemplates/**").access("hasAuthority('interview_template_view')")
                .antMatchers(HttpMethod.PUT, "/interviewTemplates").hasAuthority("interview_template_add")
                .antMatchers(HttpMethod.PUT, "/interviewTemplates/*").hasAuthority("interview_template_modify")
                .antMatchers(HttpMethod.DELETE, "/interviewTemplates/*").hasAuthority("interview_template_delete")
                //机构管理
                .antMatchers(HttpMethod.GET, "/orgnizations/**").access("hasAuthority('org_view')")
                .antMatchers(HttpMethod.POST, "/orgnizations").access("hasAuthority('org_add')")
                .antMatchers(HttpMethod.DELETE, "/orgnizations/*").access("hasAuthority('org_delete')")
                .antMatchers(HttpMethod.PUT, "/orgnizations/*/merge").access("hasAuthority('org_merge')")
                .antMatchers(HttpMethod.POST, "/orgnizations/*").hasAnyAuthority("org_modify", "org_parameter")
                .antMatchers(HttpMethod.POST, "/orgnizations/*/maintains/*").hasAnyAuthority("org_audit")
                //商户管理
                .antMatchers(HttpMethod.GET, "/customers/**").access("hasAuthority('customer_view')")
                .antMatchers(HttpMethod.PUT, "/customers").hasAuthority("customer_add")
                .antMatchers(HttpMethod.PUT, "/customers/*").hasAuthority("customer_modify")
                .antMatchers(HttpMethod.DELETE, "/customers/*").hasAuthority("customer_delete")
                .antMatchers(HttpMethod.POST, "/customers/import").hasAuthority("customer_import")
                .antMatchers(HttpMethod.POST, "/customers/export").hasAuthority("customer_export")
                .antMatchers(HttpMethod.POST, "/customers/*/appoint").hasAuthority("customer_appoint")
                //回访管理

                .antMatchers("/interviews/**").access("hasAuthority('interview_view')")
                .antMatchers(HttpMethod.PUT, "/interviews").access("hasAuthority('interview_execute')")
                .antMatchers(HttpMethod.PUT, "/interviews/*/metadata/*/*/*").access("hasAuthority('interview_execute')")
                .antMatchers("/interviews/*/data/*/rawdata/parts").access("hasAuthority('interview_execute')")
                .antMatchers(HttpMethod.PUT, "/interviews/*/back").hasAnyAuthority("interview_audit", "interview_check")
                .antMatchers(HttpMethod.PUT, "/interviews/{interviewid}/audit").hasAnyAuthority("interview_audit", "interview_check")
                //.antMatchers("/interviews/*/data/*/rawdata ").permitAll()
                //报告
                //.antMatchers(HttpMethod.GET,"/report/**").access("hasAuthority('interview_report')")
                //公告
                .antMatchers(HttpMethod.PUT, "/notice").hasAnyAuthority("notice_add")
                .antMatchers(HttpMethod.DELETE, "/notice/*").hasAnyAuthority("notice_delete")
                //用户
                .antMatchers(HttpMethod.GET, "/users/**").access("hasAuthority('user_view')")
                .antMatchers(HttpMethod.PUT, "/users").access("hasAuthority('user_add')")
                .antMatchers(HttpMethod.PUT, "/users/*").access("hasAuthority('user_modify')")
                .antMatchers(HttpMethod.GET, "/users/*/password/reset").access("hasAuthority('user_modify')")
                .antMatchers(HttpMethod.GET, "/users/*/password/unlock").access("hasAuthority('user_modify')")
                //用户日
                //.antMatchers("/userlog/**").access("hasAuthority('user_log')")
                //app权限
                .antMatchers(HttpMethod.PUT, "/app/*/*/version/*/log/*").access("hasAuthority('app_log_upload')")
                .antMatchers(HttpMethod.GET, "/app/*/*/version/*/log/*/*").hasAuthority("app_log_download")
                .antMatchers(HttpMethod.PUT, "/app").hasAuthority("app_add")
                .anyRequest().authenticated()

                .and()
                .logout().logoutUrl("/logout")
                .deleteCookies()
                .invalidateHttpSession(true);
//		.logoutSuccessHandler(new LogouthHandler());

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("*"));
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        configuration.setExposedHeaders(ImmutableList.of("WWW-Authenticate"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        //configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}