package empresa.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import empresa.dao.CabPedidosDAO;
import empresa.dao.LineasPedDAO;
import empresa.models.CabPedidos;
import empresa.models.LineasPed;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportPedidoPDF {

    public static void exportarPedido(CabPedidos pedido) {
        try {
            // Crear carpeta pdf/ si no existe
            File carpeta = new File("pdf");
            if (!carpeta.exists()) carpeta.mkdir();

            // Ruta del archivo PDF
            String rutaSalida = "pdf/Pedido_" + pedido.getNumPed() + ".pdf";

            // Crear documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
            document.open();

            // Fuentes
            BaseFont bf = BaseFont.createFont("fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fuente = new Font(bf, 12);
            Font fuenteBold = new Font(bf, 14, Font.BOLD);
            Font fuenteTitulo = new Font(bf, 20, Font.BOLD);

            // Logo y empresa
            Image logo = Image.getInstance("C:\\Users\\jdomi\\OneDrive\\Escritorio\\CRUDEmpresaCopia02\\erpUI\\src\\main\\resources\\images\\corporate_logo_white_50_53.png");
            logo.scaleAbsolute(50f, 53f);
            logo.setAbsolutePosition(50, 750);
            document.add(logo);

            Paragraph nombreEmpresa = new Paragraph("Computer, S.L.", fuenteTitulo);
            nombreEmpresa.setAlignment(Element.ALIGN_RIGHT);
            document.add(nombreEmpresa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("PEDIDO N\u00BA: " + pedido.getNumPed(), fuente));
            document.add(new Paragraph("Fecha: " + pedido.getFechaPed(), fuente));
            document.add(new Paragraph("Estado: " + pedido.getEstadoPed(), fuente));
            document.add(new Paragraph("Proveedor: " + pedido.getProveedor().getNombreProv(), fuente));
            document.add(new Paragraph("Usuario: " + pedido.getUsuario().getIdUsuarios(), fuente));
            document.add(new Paragraph("IVA: " + pedido.getIvaPed(), fuente));
            document.add(new Paragraph("\n"));

            // Tabla de productos
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Producto", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Precio Unitario", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Subtotal", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Almacén", fuenteBold)));

            LineasPedDAO lineasPedDAO = new LineasPedDAO();
            List<LineasPed> lineas = lineasPedDAO.readAll();
            double totalSinIVA = 0.0;

            for (LineasPed linea : lineas) {
                if (linea.getCabPedidos().getNumPed().equals(pedido.getNumPed())) {
                    table.addCell(new Phrase(linea.getProducto().getNombreProd(), fuente));
                    table.addCell(new Phrase(String.valueOf(linea.getCantidadProdPed()), fuente));
                    table.addCell(new Phrase(String.valueOf(linea.getPrecioProdPed()), fuente));
                    table.addCell(new Phrase(String.valueOf(linea.getSubtotalPed()), fuente));
                    table.addCell(new Phrase(linea.getAlmacen().getNombreAlm(), fuente));
                    totalSinIVA += linea.getSubtotalPed();
                }
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            double iva = pedido.getIvaPed();
            double total = totalSinIVA + (totalSinIVA * iva / 100);
            document.add(new Paragraph("Total sin IVA: " + totalSinIVA + " \u20AC", fuente));
            document.add(new Paragraph("Total con IVA (" + iva + "%): " + total + " \u20AC", fuente));

            document.close();

            // Abrir PDF automáticamente
            Desktop.getDesktop().open(new File(rutaSalida));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al generar el PDF del pedido.");
        }
    }
}

