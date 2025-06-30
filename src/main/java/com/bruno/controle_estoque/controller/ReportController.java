package com.bruno.controle_estoque.controller;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.dto.response.StockValueResponseDTO;
import com.bruno.controle_estoque.model.Product;
import com.bruno.controle_estoque.service.ReportService;
import com.bruno.controle_estoque.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE_ESTOQUE') or hasRole('VISUALIZADOR')")
public class ReportController {

    @Autowired private ReportService reportService;

    @GetMapping(value = "/movements", produces = "application/pdf")
    public ResponseEntity<byte[]> getMovementsByPeriodPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<MovementResponseDTO> movements = reportService.getMovementsByPeriod(startDateTime, endDateTime);

        byte[] pdfBytes = PdfGenerator.generateMovementsReport(movements);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Disposition", "attachment; filename=relatorio_movimentacoes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);

    }

    @GetMapping(value = "/stock/total-value", produces = "application/pdf")
    public ResponseEntity<byte[]> getTotalStockValuePdf() {
        List<Product> products = reportService.getAllProductsForStockReport();
        byte[] pdf = PdfGenerator.generateStockValueReport(products);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Disposition", "attachment; filename=valor_total_estoque.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
