package com.lucasmoraist.luflix.infrastructure.config.swagger.routes;

import com.lucasmoraist.luflix.infrastructure.api.web.request.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.lucasmoraist.luflix.infrastructure.api.handler.dto.DataValidationException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public interface UserSwagger {

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria um novo usuário na plataforma com nome, email e senha"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos ou email já cadastrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataValidationException.class)
                    )
            )
    })
    ResponseEntity<Void> register(@Valid @RequestBody UserRequest request);

}
