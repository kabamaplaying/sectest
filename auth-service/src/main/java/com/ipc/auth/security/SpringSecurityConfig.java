/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */

package com.ipc.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService usuarioService;
    
    @Autowired
    private AuthenticationEventPublisher eventPublisher;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
        //.and().authenticationEventPublisher(eventPublisher);
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManager()
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }
    
}
