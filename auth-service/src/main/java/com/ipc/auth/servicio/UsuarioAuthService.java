/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */

package com.ipc.auth.servicio;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ipc.auth.ws.UsuarioFeingClient;
import com.ipc.commons.models.dto.usuario.UsuarioDto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */
@Service
@Slf4j
public class UsuarioAuthService implements UsuarioService,  UserDetailsService
{
    
    @Autowired
    @Getter
    private UsuarioFeingClient cliente;
    
    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UsuarioDto usuario = getCliente().buscarUsuario(username).getBody();
        if (usuario == null)
        {
            log.info("El usuario que intentas buscar no existe");
            throw new UsernameNotFoundException("El usuario que intentas buscar no existe");
        }
        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).peek(System.out::println).collect(Collectors.toList());
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnable(), true, true, true, authorities);
    }

    /* (non-Javadoc)
     * @see com.ipc.auth.servicio.UsuarioService#findByusername(java.lang.String)
     */
    @Override
    public UsuarioDto findByusername(String username)
    {
        return getCliente().buscarUsuario(username).getBody();
    }

    /* (non-Javadoc)
     * @see com.ipc.auth.servicio.UsuarioService#update(com.ipc.commons.models.dto.usuario.UsuarioDto, java.lang.Long)
     */
    @Override
    public UsuarioDto update(@Valid UsuarioDto usuario, @Valid Long id)
    {
        return getCliente().actualizar(usuario).getBody();
        
    }
    
}
