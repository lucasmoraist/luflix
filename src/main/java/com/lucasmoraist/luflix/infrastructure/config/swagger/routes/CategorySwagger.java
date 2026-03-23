package com.lucasmoraist.luflix.infrastructure.config.swagger.routes;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.category.VideoByCategoryResponse;
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

@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de vídeos")
@SecurityRequirement(name = "Bearer Authentication")
public interface CategorySwagger {

    @Operation(
            summary = "Listar todas as categorias",
            description = "Retorna uma lista paginada de todas as categorias cadastradas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorias retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            )
    })
    ResponseEntity<Page<Category>> getAllVideos(
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna os detalhes de uma categoria específica pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content
            )
    })
    ResponseEntity<Category> getVideoById(
            @Parameter(description = "ID da categoria", required = true, example = "1")
            @PathVariable Long categoryId
    );

    @Operation(
            summary = "Listar vídeos por categoria",
            description = "Retorna uma lista paginada de vídeos pertencentes a uma categoria específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de vídeos da categoria retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content
            )
    })
    ResponseEntity<Page<VideoByCategoryResponse>> getVideosByCategoryId(
            @Parameter(description = "ID da categoria", required = true, example = "1")
            @PathVariable Long categoryId,
            @Parameter(description = "Número da página (inicia em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "Criar nova categoria",
            description = "Cria uma nova categoria com título e cor"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria criada com sucesso",
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
    ResponseEntity<Void> createVideo(@Valid @RequestBody CategoryRequest request);

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria atualizada com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content
            )
    })
    ResponseEntity<Void> updateVideo(
            @Parameter(description = "ID da categoria", required = true, example = "1")
            @PathVariable Long categoryId,
            @RequestBody CategoryRequest request
    );

    @Operation(
            summary = "Deletar categoria",
            description = "Remove uma categoria existente pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Categoria deletada com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - Token JWT ausente ou inválido",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada",
                    content = @Content
            )
    })
    ResponseEntity<Void> deleteVideo(
            @Parameter(description = "ID da categoria", required = true, example = "1")
            @PathVariable Long categoryId
    );

}
