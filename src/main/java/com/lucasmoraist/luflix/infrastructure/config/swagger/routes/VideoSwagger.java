package com.lucasmoraist.luflix.infrastructure.config.swagger.routes;

import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.video.FindAllVideoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Vídeos", description = "Endpoints para gerenciamento de vídeos")
public interface VideoSwagger {

    @Operation(
            summary = "Listar todos os vídeos",
            description = "Retorna uma lista paginada de todos os vídeos cadastrados, com opção de busca por título"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de vídeos retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            )
    })
    ResponseEntity<Page<FindAllVideoResponse>> getAllVideos(
            @Parameter(description = "Termo de busca para filtrar vídeos pelo título", example = "Spring Boot")
            @RequestParam(required = false, value = "search") String title,
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "Buscar vídeo por ID",
            description = "Retorna os detalhes de um vídeo específico pelo seu ID"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Vídeo encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Video.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vídeo não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<Video> getVideoById(
            @Parameter(description = "ID do vídeo", required = true, example = "1")
            @PathVariable Long videoId
    );

    @Operation(
            summary = "Listar vídeos gratuitos",
            description = "Retorna uma lista paginada de vídeos gratuitos disponíveis sem autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de vídeos gratuitos retornada com sucesso"
            )
    })
    ResponseEntity<Page<FindAllVideoResponse>> getAllVideosFree(
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "Criar novo vídeo",
            description = "Cria um novo vídeo com título, descrição, URL e categoria associada"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Vídeo criado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de requisição inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            )
    })
    ResponseEntity<Void> createVideo(@Valid @RequestBody VideoRequest request);

    @Operation(
            summary = "Atualizar vídeo",
            description = "Atualiza os dados de um vídeo existente pelo seu ID"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Vídeo atualizado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vídeo não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<Void> updateVideo(
            @Parameter(description = "ID do vídeo", required = true, example = "1")
            @PathVariable Long videoId,
            @RequestBody VideoRequest request
    );

    @Operation(
            summary = "Deletar vídeo",
            description = "Remove um vídeo existente pelo seu ID"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Vídeo deletado com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vídeo não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<Void> deleteVideo(
            @Parameter(description = "ID do vídeo", required = true, example = "1")
            @PathVariable Long videoId
    );

}
