package empresa.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import empresa.dao.CabFacturasDAO;
import empresa.dao.LineasFactDAO;
import empresa.models.CabFacturas;
import empresa.models.LineasFact;
import empresa.utils.InputHelper;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.text.DecimalFormat;

public class ExportFacturaPDF {

    public static void exportarFactura(CabFacturas factura){

        DecimalFormat df = new DecimalFormat("#0.00");

        if(factura == null){
            System.out.println("Factura nula, no se puede exportar");
            return;
        }

        try{
            // Crear la carpeta pdf/, si no existe
            File carpeta = new File("pdf");
            if(!carpeta.exists()) carpeta.mkdir();

            // Crear documento pdf
            Document document = new Document();
            String rutaSalida = "pdf/Factura_" + factura.getNumFact() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
            document.open();

            // Cargar fuente
            BaseFont bf = BaseFont.createFont("fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fuente = new Font(bf, 12);
            Font fuenteBold = new Font(bf, 14, Font.BOLD);
            Font fuenteTitulo = new Font(bf, 20, Font.BOLD);

            // Logo y nombre de la empresa
            Image logo = Image.getInstance("C:\\Users\\jdomi\\OneDrive\\Escritorio\\CRUDEmpresaCopia02\\erpUI\\src\\main\\resources\\images\\corporate_logo_white_50_53.png");
            logo.scaleAbsolute(50f, 53f); // Ajuste del tamaño
            logo.setAbsolutePosition(50, 750);
            document.add(logo);

            Paragraph nombreEmpresa = new Paragraph("Computer, S.L.", fuenteTitulo);
            nombreEmpresa.setAlignment(Element.ALIGN_RIGHT);
            document.add(nombreEmpresa);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("FACTURA N\u00BA: " + factura.getNumFact(), fuente));
            document.add(new Paragraph("Fecha: " + factura.getFechaFact(), fuente));
            document.add(new Paragraph("Estado: " + factura.getEstadoFact(), fuente));
            document.add(new Paragraph("Cliente: " + factura.getCliente().getNombreCli(), fuente));
            document.add(new Paragraph("Usuario: " + factura.getUsuario().getIdUsuarios(), fuente));
            document.add(new Paragraph("IVA: " + factura.getIvaFact(), fuente));
            document.add(new Paragraph("\n"));

            // Tabla de productos
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Producto", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Precio Unitario", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Subtotal", fuenteBold)));
            table.addCell(new PdfPCell(new Phrase("Almac\u00E9n", fuenteBold)));

            LineasFactDAO lineasFactDAO = new LineasFactDAO();
            List<LineasFact> lineas = lineasFactDAO.readAll();
            double totalSinIVA = 0.0;

            for (LineasFact linea : lineas) {
                if (linea.getFactura().getNumFact().equals(factura.getNumFact())) {
                    table.addCell(new Phrase(linea.getProducto().getNombreProd(), fuente));
                    table.addCell(new Phrase(String.valueOf(linea.getCantidadProdFact()), fuente));
                    table.addCell(new Phrase(df.format(linea.getPrecioProdFact()), fuente));
                    table.addCell(new Phrase(df.format(linea.getSubtotalFact()), fuente));
                    table.addCell(new Phrase(linea.getAlmacen().getNombreAlm(), fuente));
                    totalSinIVA += linea.getSubtotalFact();
                }
            }
            document.add(table);
            document.add(new Paragraph("\n"));

            double iva = factura.getIvaFact();
            double total = totalSinIVA + (totalSinIVA * iva / 100);
            document.add(new Paragraph("Total sin IVA: " + df.format(totalSinIVA) + " \u20AC", fuente));
            document.add(new Paragraph("Total con IVA (" + df.format(iva) + "%): " + df.format(total) + " \u20AC)", fuente));

            document.close();
            System.out.println("Factura exportada correctamente a " + rutaSalida);

            // Abrir el archivo PDF automáticamente (solo si el sistema lo permite)
            if(Desktop.isDesktopSupported()){
                try{
                    File pdfFile = new File(rutaSalida);
                    Desktop.getDesktop().open(pdfFile);
                }catch (Exception ex){
                    System.err.println("No se puede abrir el archivo de pdf");
                    ex.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Ocurrió un error al generar el PDF");
        }
    }
}




/*import empresa.dao.CabFacturasDAO;
import empresa.dao.LineasFactDAO;
import empresa.models.CabFacturas;
import empresa.models.LineasFact;
import empresa.utils.InputHelper;
import empresa.models.Productos;
import empresa.models.Almacen;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExportFacturaPDF {

    public static void exportarFactura(){
        System.out.println("\n<<< EXPORTAR FACTURA A PDF >>>");

        Long id = InputHelper.leerLong("Introduzca el ID de la factura a exportar");

        CabFacturasDAO cabFacturasDAO = new CabFacturasDAO();
        CabFacturas factura = cabFacturasDAO.readById(id);

        if(factura == null){
            System.out.println("No se encontró la factura con el ID: " + id);
            return;
        }

        try (PDDocument document = new PDDocument()){
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            PDType0Font font = PDType0Font.load(document, new File("fonts/PlaywriteHU-VariableFont_wght.ttf"));

            PDImageXObject logo = PDImageXObject.createFromFile("C:\\Users\\jdomi\\OneDrive\\Escritorio\\CRUDEmpresaCopia02\\erpUI\\src\\main\\resources\\images\\corporate_logo_white_50_53.png", document);
            contentStream.drawImage(logo, 50, 750, 50, 53);

            contentStream.beginText();
            contentStream.setFont(font, 18);
            contentStream.newLineAtOffset(400, 770);
            contentStream.showText("Computer, S.L.");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(50, 700);

            contentStream.showText("FACTURA Nº: " + factura.getNumFact());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Fecha: " + factura.getFechaFact());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Estado: " + factura.getEstadoFact());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Cliente: " + factura.getCliente().getNombreCli());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Usuario: " + factura.getUsuario().getIdUsuarios());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("IVA: " + factura.getIvaFact() + " %");
            contentStream.endText();

            LineasFactDAO lineasFactDAO = new LineasFactDAO();
            List<LineasFact> lineas = lineasFactDAO.readAll();

            float y = 550;
            float margin = 50;
            float rowHeight = 20;
            float cellMargin = 5;

            String[] headers = {"Producto", "Cantidad", "Precio Unitario", "Subtotal", "Almacén"};
            float[] columnWidths = {150, 70, 90, 90, 100};

            contentStream.setFont(font, 12);
            float x = margin;

            for (int i = 0; i < headers.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(x + cellMargin, y);
                contentStream.showText(headers[i]);
                contentStream.endText();
                x += columnWidths[i];
            }

            y -= rowHeight;
            double totalSinIVA = 0.0;

            for (LineasFact linea : lineas) {
                if (linea.getFactura().getNumFact().equals(factura.getNumFact())) {
                    x = margin;

                    String[] datos = {
                            linea.getProducto().getNombreProd(),
                            String.valueOf(linea.getCantidadProdFact()),
                            String.valueOf(linea.getPrecioProdFact()),
                            String.valueOf(linea.getSubtotalFact()),
                            linea.getAlmacen().getNombreAlm()
                    };

                    for (int i = 0; i < datos.length; i++) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x + cellMargin, y);
                        contentStream.showText(datos[i]);
                        contentStream.endText();
                        x += columnWidths[i];
                    }

                    y -= rowHeight;
                    totalSinIVA += linea.getSubtotalFact();
                }
            }

            double iva = factura.getIvaFact();
            double total = totalSinIVA + (totalSinIVA * iva / 100);

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(50, y - 30);
            contentStream.showText("Total sin IVA: " + totalSinIVA + " €");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Total con IVA (" + iva + "%): " + total + " €");
            contentStream.endText();

            System.out.println("Fuente cargada: " + font.getName());

            contentStream.close();

            String rutaSalida = "pdf/Factura_" + factura.getNumFact() + "_PDFBox.pdf";
            document.save(rutaSalida);
            System.out.println("Factura exportada correctamente a " + rutaSalida);

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Ocurrió un error al generar el PDF");
        }
    }
}*/
