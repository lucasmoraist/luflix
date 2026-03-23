package com.lucasmoraist.luflix.infrastructure.config.swagger.routes;

import com.lucasmoraist.luflix.infrastructure.api.web.request.AuthenticationRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.token.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public interface AuthenticationSwagger {

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com email e senha, retornando um token JWT para acesso aos endpoints protegidos",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas",
                    content = @Content
            )
    })
    ResponseEntity<TokenDTO> register(@Valid @RequestBody AuthenticationRequest request);

}
