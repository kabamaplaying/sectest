/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/27
 * @since 0.0.1 2020/04/27
 */

package com.ipc.auth.servicio;

import javax.validation.Valid;

import com.ipc.commons.models.dto.usuario.UsuarioDto;

/**
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/27
 * @since 0.0.1 2020/04/27
 */
public interface UsuarioService
{
    /**
     * @author Kaleth Baena
     * @version 0.0.1 2020/04/28
     * @since 0.0.1 2020/04/28
     * @param username
     * @return
     */
    UsuarioDto findByusername(String username);
    
    /**
     * @author Kaleth Baena
     * @version 0.0.1 2020/04/28
     * @since 0.0.1 2020/04/28
     * @param usuario
     * @param id
     * @return
     */
    UsuarioDto update(@Valid UsuarioDto usuario, @Valid Long id);
}
