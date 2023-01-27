package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	//http 에서 제공하는 기본 로그인 방식. 
        http.formLogin()
        // 로그인 페이지를 지정. 
                .loginPage("/members/login")
                // 로그인 후 기본 페이지 위치 설정. 
                .defaultSuccessUrl("/")
                // 인증의 수단을 예) username, email. 등. 
                .usernameParameter("email")
                // 인증 실패시 가게될 페이지.
                .failureUrl("/members/login/error")
                .and()
                .logout()
                // 로그 아웃 페이지.
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                // 로그 아웃 정상 수행 후 가게될 기본페이지 설정. 
                .logoutSuccessUrl("/")
        ;

        // 허가 된 요청의 정보의 매칭 여부. 
        http.authorizeRequests()
        // css, js, img 폴더 하위에 모든 경로는 모두 허용. 
                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
                // 컨트롤로에서 허용된 url 매핑 주소를 등록. 
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**","/thymeleaf/**").permitAll()
                //hasRole 에서 정의된 롤로만 접근 가능한 페이지.
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                // 그외에 어떤 요청들은 전부다 인증이 되면 허용하겠다. 
                .anyRequest().authenticated()
        ;

        //package com.shop.config; 이경로에 
        // CustomAuthenticationEntryPoint 에서 
        // 예외 처리하는 메서드 있고, 문자열은 : Unauthorized
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;

        return http.build();
    }

    // 해당 패스워드를 디비에 저장시, 중간에 가로채기를 당해도, 패스워드가 암호화가 걸리게 하는 설정. 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}