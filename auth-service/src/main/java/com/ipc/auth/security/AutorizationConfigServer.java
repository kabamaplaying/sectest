/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */

package com.ipc.auth.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */
@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AutorizationConfigServer extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private InfoAdicionalToken infoAdicionalToken;
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer)
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        // permios para dar seguridad a los endpoints
        security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer)
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.inMemory().withClient("frontendapp").secret(passwordEncoder.encode("12345")).scopes("read", "write").authorizedGrantTypes("password", "refresh_token")
        .accessTokenValiditySeconds(3600)
        .refreshTokenValiditySeconds(3600);
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        //encadena objetos tokenAcces, sirve para agregar la informacion adicional al token de acceso del endpoint 
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
        // 1
        endpoints.authenticationManager(authenticationManager)
                // 2
                .tokenStore(tokenStore())
                // 3
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }
    
    /**
     * @author Kaleth Baena
     * @version 0.0.1 2020/04/22
     * @since 0.0.1 2020/04/22
     * @return
     */
    @Bean
    public JwtTokenStore tokenStore()
    {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    /**
     * @author Kaleth Baena
     * @version 0.0.1 2020/04/22
     * @since 0.0.1 2020/04/22
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter()
    {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
        return tokenConverter;
    }
    
}
