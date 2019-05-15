/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import static com.itextpdf.kernel.pdf.collection.PdfCollectionField.TEXT;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author cao
 */
public class createPdf {
   public void createPdf() throws IOException {
       String dest = "src/resources/codigo.pdf";
    PdfWriter writer = new PdfWriter(dest);
    PdfDocument pdfDoc = new PdfDocument(writer);
    Document doc = new Document(pdfDoc, PageSize.LETTER);
    
    
    
    Paragraph empty = new Paragraph("");
Table tabla1 = new Table(2);
tabla1.setWidth(500);
Paragraph nom = new Paragraph("NOMBRE");
Paragraph cif = new Paragraph("CIF: ");
Paragraph dir1 = new Paragraph("Avenida de la facultad - 6");
Paragraph dir2 = new Paragraph("24001 León");
Cell cell1 = new Cell();
cell1.setBorder(new SolidBorder(1));
cell1.setWidth(250);
cell1.setTextAlignment(TextAlignment.CENTER);
cell1.add(nom);
cell1.add(cif);
cell1.add(dir1);
cell1.add(dir2);
tabla1.addCell(cell1);
Cell cell2 = new Cell();
cell2.setBorder(Border.NO_BORDER);
cell2.setPadding(10);
cell2.setTextAlignment(TextAlignment.RIGHT);
cell2.add(new Paragraph("IBAN: "));
cell2.add(new Paragraph("Bruto anual: "));
cell2.add(new Paragraph("Categoría: "));
cell2.add(new Paragraph("Fecha de alta: "));
tabla1.addCell(cell2);Table tabla2 = new Table(2);
tabla2.setWidth(500);
empty.add(tabla1);
empty.add(tabla2);

/*Image img = new Image(ImageDataFactory.create(imagen));
img.setBorder(Border.NO_BORDER);
img.setPadding(10);*/
Cell cell3 = new Cell();
//cell3.add(img);
cell3.setBorder(Border.NO_BORDER);
cell3.setPaddingLeft(23);
cell3.setPaddingTop(20);
cell3.setWidth(250);
tabla2.addCell(cell3);
doc.add(empty);
doc.close();
    
}
    
}
