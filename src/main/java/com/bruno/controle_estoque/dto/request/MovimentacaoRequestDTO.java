package com.bruno.controle_estoque.dto.request;

import com.bruno.controle_estoque.enums.TipoMovimentacao;
import com.bruno.controle_estoque.model.Produto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovimentacaoRequestDTO {
    private Produto produto;
    private TipoMovimentacao tipoMovimentacao;
    private Integer quantidade;
    private LocalDateTime dataMovimentacao;
}
