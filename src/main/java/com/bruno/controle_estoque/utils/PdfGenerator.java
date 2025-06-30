package com.bruno.controle_estoque.utils;

import com.bruno.controle_estoque.dto.response.MovementResponseDTO;
import com.bruno.controle_estoque.model.Product;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PdfGenerator {

    public static byte[] generateMovementsReport(List<MovementResponseDTO> movements) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            //titulo
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Relatório de Movimentações de Estoque", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            if (movements.isEmpty()) {

                Font fontMessage = FontFactory.getFont(FontFactory.HELVETICA, 12);
                Paragraph message = new Paragraph("Nenhuma movimentação encontrada no período informado.", fontMessage);
                message.setAlignment(Element.ALIGN_CENTER);
                document.add(message);

            } else {

                //tabela
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{2, 4, 2, 4});

                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

                //cabecalhos
                table.addCell(new PdfPCell(new Phrase("ID", headFont)));
                table.addCell(new PdfPCell(new Phrase("Produto", headFont)));
                table.addCell(new PdfPCell(new Phrase("Tipo", headFont)));
                table.addCell(new PdfPCell(new Phrase("Data", headFont)));

                for (MovementResponseDTO m : movements) {
                    table.addCell(String.valueOf(m.getId()));
                    table.addCell(m.getProduct().getName());
                    table.addCell(m.getTypeMovement().name());
                    table.addCell(m.getDataMovement().toString());
                }

                document.add(table);

            }

            document.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return out.toByteArray();
    }


    public static byte[] generateStockValueReport(List<Product> products) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Relatório de Valor Total do Estoque", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            if (products.isEmpty()) {
                Paragraph message = new Paragraph("Nenhum produto encontrado no estoque.");
                message.setAlignment(Element.ALIGN_CENTER);
                document.add(message);
            } else {
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{2, 5, 2, 2, 3});

                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                table.addCell(new PdfPCell(new Phrase("ID", headFont)));
                table.addCell(new PdfPCell(new Phrase("Produto", headFont)));
                table.addCell(new PdfPCell(new Phrase("Quantidade", headFont)));
                table.addCell(new PdfPCell(new Phrase("Preço", headFont)));
                table.addCell(new PdfPCell(new Phrase("Total", headFont)));

                BigDecimal totalValue = BigDecimal.ZERO;

                for (Product p : products) {
                    BigDecimal productTotal = p.getUnitPrice().multiply(BigDecimal.valueOf(p.getQuantStock()));
                    totalValue = totalValue.add(productTotal);

                    table.addCell(String.valueOf(p.getId()));
                    table.addCell(p.getName());
                    table.addCell(String.valueOf(p.getQuantStock()));
                    table.addCell(currencyFormat.format(p.getUnitPrice()));
                    table.addCell(currencyFormat.format(productTotal));
                }

                document.add(table);

                Paragraph total = new Paragraph("\nValor total do estoque: R$ " + totalValue,
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
                total.setAlignment(Element.ALIGN_RIGHT);
                document.add(total);
            }

            document.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return out.toByteArray();
    }

}
