/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/27
 * @since 0.0.1 2020/04/27
 */

package com.ipc.auth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.ipc.auth.servicio.UsuarioService;
import com.ipc.commons.models.dto.usuario.UsuarioDto;

/**
 * Componente que agrega informacion adicional a los claims del jwtoken
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/27
 * @since 0.0.1 2020/04/27
 */
@Component
public class InfoAdicionalToken implements TokenEnhancer
{
    
    @Autowired
    private UsuarioService usuarioService;

    /* (non-Javadoc)
     * @see org.springframework.security.oauth2.provider.token.TokenEnhancer#enhance(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication)
    {
        Map<String, Object> info = new HashMap<>();
        UsuarioDto usuario = usuarioService.findByusername(authentication.getName());
        info.put("nombre", usuario.getNombre());
        info.put("apellido", usuario.getApellido());
        info.put("correo", usuario.getEmail());
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
        return accessToken;
    }
    
}
