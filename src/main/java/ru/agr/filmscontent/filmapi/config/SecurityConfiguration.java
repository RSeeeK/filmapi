package ru.agr.filmscontent.filmapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import ru.agr.filmscontent.filmapi.controller.exception.FilterChainExceptionHandler;
import ru.agr.filmscontent.filmapi.db.entity.RolePermission;
import ru.agr.filmscontent.filmapi.security.jwt.JwtSecurityConfigurer;
import ru.agr.filmscontent.filmapi.security.jwt.JwtTokenProvider;

/**
 * Security configuration class
 *
 * @author Arslan Rabadanov
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui",
            "/webjars/**",
            "/swagger-resources/**",
            "/swagger-resources",
            // -- authenticate
            "/api/v1/auth/login",
            //old api
            "/movies",
            "/movies/**",
            "/genre",
            "/"
    };

    private static final String[] WHITELIST_GET = {
            "/api/v1/movies",
            "/api/v1/movies/**",
            "/api/v1/genres",
            "/api/v1/genres/**"
    };

    private static final String[] WHITELIST_POST = {
            "/api/v1/users/register"
    };

    private final JwtTokenProvider jwtTokenProvider;

    private final FilterChainExceptionHandler filterChainExceptionHandler;

    public SecurityConfiguration(FilterChainExceptionHandler filterChainExceptionHandler,
            JwtTokenProvider jwtTokenProvider) {
        super(false);
        this.jwtTokenProvider = jwtTokenProvider;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, WHITELIST_GET).permitAll()
                .antMatchers(HttpMethod.POST, WHITELIST_POST).permitAll()
                .antMatchers( "/api/v1/users/current").authenticated()
                .antMatchers( "/api/v1/users/current/change/password").authenticated()
                .antMatchers( "/api/v1/users/**", "/api/v1/roles/**").hasAuthority(RolePermission.Authority.USER_ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
