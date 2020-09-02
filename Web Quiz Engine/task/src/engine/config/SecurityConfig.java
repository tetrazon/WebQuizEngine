package engine.config;

import engine.security.CustomAuthenticationProvider;
import engine.security.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    UserRepositoryUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
        .and()
        .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
        .and()
        .authenticationProvider(authProvider);
        //auth.userDetailsService(userDetailsService)
        //.passwordEncoder(passwordEncoder()); // configure custom user details service
        //auth.authenticationProvider(authProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/api/register", "/actuator/shutdown");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
               /* .headers()
                .frameOptions()
                .disable();*/
   /*     httpSecurity.authorizeRequests()
                .antMatchers("/api/register").access("permitAll")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedPage("/403");*/
                /*.authorizeRequests()
//                .antMatchers("/api/quizzes/**")
//                .hasRole("USER")
                .antMatchers("/api/register", "/actuator/shutdown", "/h2-console/**")
                .access("permitAll")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                ;*/

    }
}
