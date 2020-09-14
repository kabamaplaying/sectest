/**
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGIA DE DESARROLLO DE
 * Baena Y SE ENCUENTRA PROTEGIDO POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */

package com.ipc.auth.ws;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ipc.commons.models.dto.usuario.UsuarioDto;

/**
 * @author Kaleth Baena
 * @version 0.0.1 2020/04/22
 * @since 0.0.1 2020/04/22
 */
@FeignClient(name = "usuario-service")
public interface UsuarioFeingClient
{
    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> buscarUsuario(@PathVariable String username);
    
    @PutMapping(path = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> actualizar(@RequestBody @Valid UsuarioDto usuario);
}
