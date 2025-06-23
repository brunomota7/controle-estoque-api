package com.bruno.controle_estoque.dto.response;

import com.bruno.controle_estoque.enums.TipoMovimentacao;
import com.bruno.controle_estoque.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoResponseDTO {
    private Long id;
    private Produto produto;
    private TipoMovimentacao tipoMovimentacao;
    private Integer quantidade;
    private LocalDateTime dataMovimentacao;
}
