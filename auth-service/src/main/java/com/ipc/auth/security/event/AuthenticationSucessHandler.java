/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/28
 * @since 0.0.1 2020/04/28
 */

package com.ipc.auth.security.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ipc.auth.servicio.UsuarioService;
import com.ipc.commons.models.dto.usuario.UsuarioDto;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/28
 * @since 0.0.1 2020/04/28
 */
@Component
@Slf4j
public class AuthenticationSucessHandler implements AuthenticationEventPublisher

{
    
    @Autowired
    private UsuarioService usuarioService;
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationEventPublisher#publishAuthenticationSuccess(org.springframework.security.core.Authentication)
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication)
    {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info(String.format("Usuario logeado: %s", user.getUsername()));
        
        UsuarioDto resultFind = usuarioService.findByusername(authentication.getName());
        
        if (resultFind.getIntentos() != null && resultFind.getIntentos() > 0)
        {
            resultFind.setIntentos(0);
            usuarioService.update(resultFind, resultFind.getId());
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationEventPublisher#publishAuthenticationFailure(org.springframework.security.core.AuthenticationException, org.springframework.security.core.Authentication)
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication)
    {
        try
        {
            log.info(String.format("Usuario logeado: %s", exception.getMessage()));
            UsuarioDto resultFind = usuarioService.findByusername(authentication.getName());
            System.out.println(resultFind.getRoles().toString());
            if (resultFind.getIntentos() == null)
            {
                resultFind.setIntentos(0);
            }
            resultFind.setIntentos(resultFind.getIntentos() + 1);
            if (resultFind.getIntentos() >= 3)
            {
                resultFind.setEnable(false);
            }
            usuarioService.update(resultFind, resultFind.getId());
        }
        catch (FeignException e)
        {
            log.info(String.format("Se presento un error al intentar actualizar el usuario %s", authentication.getName()));
        }
        
    }
    
}
