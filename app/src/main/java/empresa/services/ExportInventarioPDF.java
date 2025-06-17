package empresa.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import empresa.models.ProductosAlmacen;


import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportInventarioPDF {

    public static void exportInventario(List<ProductosAlmacen> inventario, String nombreAlmacen) {
        try{
            // Crear la carpeta pdf, si no existe
            File carpeta = new File("pdf");
            if(!carpeta.exists()){carpeta.mkdir();}

            // Ruta del archivo PDF
            String rutaSalida = "pdf/Inventario_" + nombreAlmacen + ".pdf";

            // Crear el documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
            document.open();

            // Fuentes
            BaseFont bf = BaseFont.createFont("fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fuente = new Font(bf, 12);
            Font fuenteBold = new Font(bf, 14, Font.BOLD);
            Font fuenteTitulo = new Font(bf, 20, Font.BOLD);

            // Título
            Paragraph titulo = new Paragraph("Recuento de Inventario - " + nombreAlmacen, fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            // Tabla de inventario
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("ID Producto", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Nombre Producto", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Stock Actual", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Stock Contado", fuenteBold)));

            for (ProductosAlmacen pa : inventario) {
                table.addCell(new Phrase(String.valueOf(pa.getId().getIdProducto()), fuente));
                table.addCell(new Phrase(pa.getProducto().getNombreProd(), fuente));
                table.addCell(new Phrase(String.valueOf(pa.getStock()), fuente));

                // Espacio en blanco para el recuento manual
                PdfPCell celdaVacia = new PdfPCell(new Phrase(""));
                celdaVacia.setMinimumHeight(20);
                table.addCell(celdaVacia);
            }
            document.add(table);
            document.close();

            // Abrir PDF automáticamente
            Desktop.getDesktop().open(new File(rutaSalida));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error al generar el PDF de inventario");
        }
    }
}
