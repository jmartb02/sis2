/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import static com.itextpdf.kernel.pdf.PdfName.Color;
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
import static java.awt.Color.red;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import modelo.Trabajadorbbdd;



/**
 *
 * @author cao
 */
public class createPdf  {
   public void createPdf(CalculoUnTrabajador trabajador) throws IOException {
       String dest = "src/resources/"+trabajador.getTrabajador().getNombre()+trabajador.getTrabajador().getApellido1()+".pdf";
    PdfWriter writer = new PdfWriter(dest);
    PdfDocument pdfDoc = new PdfDocument(writer);
    Document doc = new Document(pdfDoc, PageSize.LETTER);
    
    
 
Paragraph empty = new Paragraph("");
    
//creacion de tablas
Table tabla1 = new Table(1);
tabla1.setHeight(30);
tabla1.setWidth(500);

Table tabla2 = new Table(3);
tabla2.setWidth(530);

Table empleado = new Table(1);
empleado.setWidth(500);

Table empleado1 = new Table(1);
empleado1.setWidth(500);

Table tabla3 =new Table(1);

Table tabla4 = new Table(1);
tabla4.setWidth(500);

Table tabla5 = new Table(2);
tabla5.setWidth(500);




//celda empleado
Cell empleCell = new Cell();
empleCell.add(new Paragraph("Informacion Empleado")).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setUnderline();
empleCell.setBorder(Border.NO_BORDER);
empleado.addCell(empleCell);

//logo ule
Image img = new Image(ImageDataFactory.create("src/resources/ule.jpg"));
img.setBorder(Border.NO_BORDER);
Cell cell3 = new Cell();
cell3.setWidth(200);
cell3.add(img);
cell3.setBorder(Border.NO_BORDER);
tabla2.addCell(cell3);

//asignatura
Cell cell4 = new Cell();
cell4.setWidth(200);
cell4.setPaddingTop(15);
cell4.setPaddingRight(100);
cell4.setBorder(Border.NO_BORDER);
cell4.add(new Paragraph("Sistemas ")).setTextAlignment(TextAlignment.RIGHT).setBold();
cell4.add(new Paragraph("de")).setTextAlignment(TextAlignment.CENTER);;
cell4.add(new Paragraph("Informacion ")).setTextAlignment(TextAlignment.CENTER);;
cell4.add(new Paragraph("II")).setTextAlignment(TextAlignment.CENTER);;
tabla2.addCell(cell4);

//logo ingeniria
Cell cell5 = new Cell();
cell5.setWidth(200);
Image img2 = new Image(ImageDataFactory.create("src/resources/inf.jpg"));
img.setBorder(Border.NO_BORDER);
cell5.add(img2);
cell5.setBorder(Border.NO_BORDER);
cell5.setWidth(200);
tabla2.addCell(cell5);

//separador
Image img3 = new Image(ImageDataFactory.create("src/resources/separador.png"));
img.setBorder(Border.NO_BORDER);
Cell cell6 = new Cell();
cell6.setWidth(700);
cell6.add(img3);
cell6.setBorder(Border.NO_BORDER);
tabla3.addCell(cell6);

//datos empleado 
Paragraph nom = new Paragraph(" NOMBRE: "+trabajador.getTrabajador().getNombre()+ " | "+
                              " APELLIDO: "+trabajador.getTrabajador().getApellido1()+ " | "+
                               " NIF/NIE: "+trabajador.getTrabajador().getNifnie()
                             ).setFontSize(15);
       ;
Cell cell1 = new Cell();
cell1.setBorder(Border.NO_BORDER);
cell1.setWidth(500);
cell1.setHeight(20);
cell1.setTextAlignment(TextAlignment.CENTER);
cell1.add(nom);
tabla1.addCell(cell1);

//datos empleado 
Paragraph nom1 = new Paragraph(" IBAN: "+trabajador.getTrabajador().getCodigoCuenta()+ " | "+
                              " Fecha de Alta: "+trabajador.getFecha()
                             ).setFontSize(15);
       
Cell cellExtra = new Cell();
cellExtra.setBorder(Border.NO_BORDER);
cellExtra.setWidth(500);
cellExtra.setHeight(20);
cellExtra.setTextAlignment(TextAlignment.CENTER);
cellExtra.add(nom1);
empleado1.addCell(cellExtra);

//celda nomina
Cell nomina = new Cell();
nomina.add(new Paragraph("Nomina")).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setUnderline();
nomina.setBorder(Border.NO_BORDER);
tabla4.addCell(nomina);

//celda datos nomina
Cell nomina1 = new Cell();
nomina1.setBorder(new SolidBorder(1));
nomina1.setWidth(500);
nomina1.setHeight(300);
nomina1.add(new Paragraph("  ------------------------------------------Cantidad-----------imp. Unit--------Dev----------Deducc")).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT).setPaddingLeft(15);;
nomina1.add(new Paragraph("  salario base:"+"                              30 dias    "+"            "+trabajador.getCalculo().calculoMes()+"            "+trabajador.getCalculo().calculoBase())).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  prorata:"+"                                      30 dias "+"               "+trabajador.getCalculo().prorataMes()+"               "+trabajador.getCalculo().calculoProrateo())).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  complemento:"+"                            30 dias"+"               "+trabajador.getCalculo().complementoMes()+"               "+trabajador.getCalculo().calculoComplemento())).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Antiguedad:                               "+trabajador.getCalculo().calculoTrienio()+" Trienios"+"            "+trabajador.getCalculo().AntiguedadMes()+"               "+trabajador.getCalculo().calculoAntiguedad())).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Contingencias generales:           4,7%")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Desempleo:                                 1,6%")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Cuota de formacion:                   0,1%")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  IRPF:                          %")).setTextAlignment(TextAlignment.LEFT);;
tabla5.addCell(nomina1);


empty.add(tabla2);//cabecera
empty.add(tabla3);//separador
empty.add(empleado);//cabecera empleado
empty.add(tabla1);//informacion empleado
empty.add(empleado1);
empty.add(tabla3);//separador
empty.add(tabla4);//cabecera nomina
empty.add(tabla5);

doc.add(empty);
doc.close();
    
}
    
}
