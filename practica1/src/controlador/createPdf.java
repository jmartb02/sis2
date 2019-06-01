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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import modelo.Trabajadorbbdd;



/**
 *
 * @author cao
 */
public class createPdf  {
   public void createPdf(CalculoUnTrabajador trabajador, String extra,String ruta) throws IOException {
       LocalDate trabajadorDate = trabajador.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year=trabajadorDate.getYear();
        int month = trabajadorDate.getMonthValue();
        int day = trabajadorDate.getDayOfMonth();
       String dest = ruta+"/"+trabajador.getTrabajador().getNifnie()+trabajador.getTrabajador().getNombre()
               +trabajador.getTrabajador().getApellido1()+trabajador.getTrabajador().getApellido2()+year+month+day+extra+".pdf";
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

Table empresa = new Table(1);

Table empleado1 = new Table(1);
//empleado1.setWidth(500);

Table empleado2 = new Table(1);

Table tabla3 =new Table(1);

Table tabla4 = new Table(1);
tabla4.setWidth(500);

Table tabla5 = new Table(2);
//tabla5.setWidth(600);

Table tabla6 = new Table(1);
Table tabla7 = new Table(1);
tabla7.setWidth(500);

Table tabla8 = new Table(1);
tabla8.setWidth(500);


//celda empleado
Cell empleCell = new Cell();
empleCell.add(new Paragraph("Informacion Empleado")).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setUnderline();
empleCell.setBorder(Border.NO_BORDER);
empleado.addCell(empleCell);

//celda empresa
Cell empresaCell = new Cell();
empresaCell.add(new Paragraph("Informacion Empresa")).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setUnderline();
empresaCell.setBorder(Border.NO_BORDER);
empresa.addCell(empresaCell);

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
Paragraph nom = new Paragraph("Empresa: "+trabajador.getTrabajador().getEmpresas().getNombre()+
        "                                                         NOMBRE: "+trabajador.getTrabajador().getNombre()+ " "+trabajador.getTrabajador().getApellido1()+
                                " "+trabajador.getTrabajador().getApellido2()+"").setTextAlignment(TextAlignment.LEFT);
Paragraph nom11 = new Paragraph("CIF: "+trabajador.getTrabajador().getEmpresas().getCif()+
        "                                                                                 NIF/NIE: "+trabajador.getTrabajador().getNifnie()
                             ).setTextAlignment(TextAlignment.LEFT);
       
Cell cell1 = new Cell();
cell1.setBorder(Border.NO_BORDER);
//cell1.setWidth(500);
cell1.add(nom);
tabla1.addCell(cell1);

Cell cellaux2 = new Cell();
cellaux2.setBorder(Border.NO_BORDER);
//cellaux.setWidth(500);
cellaux2.add(nom11);
empleado1.addCell(cellaux2);



//datos empleado 
Paragraph nom1 = new Paragraph(" IBAN: "+trabajador.getTrabajador().getCodigoCuenta()+ " | "+
                              " Fecha de Alta: "+day+"/"+month+"/"+year
                             ).setTextAlignment(TextAlignment.RIGHT);
       
Cell cellExtra = new Cell();
cellExtra.setBorder(Border.NO_BORDER);
cellExtra.setWidth(500);
cellExtra.setTextAlignment(TextAlignment.CENTER);
cellExtra.add(nom1);
empleado1.addCell(cellExtra);

Paragraph nom2 = new Paragraph(" Categoria: "+trabajador.getTrabajador().getCategorias().getNombreCategoria()+ " | "+
                              " Bruto anual: "+trabajador.getNomina().getBrutoAnual()+"     "+extra
                             ).setTextAlignment(TextAlignment.RIGHT);
       
Cell cellExtraAnual = new Cell();
cellExtraAnual.setBorder(Border.NO_BORDER);
cellExtraAnual.setWidth(500);
cellExtraAnual.add(nom2);
empleado1.addCell(cellExtraAnual);

//celda nomina
Cell nomina = new Cell();
nomina.add(new Paragraph("Nomina")).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setUnderline();
nomina.setBorder(Border.NO_BORDER);
tabla4.addCell(nomina);

//celda datos nomina
Cell nomina1 = new Cell();
nomina1.setBorder(new SolidBorder(1));
nomina1.setWidth(500);
//nomina1.setHeight(300);
nomina1.add(new Paragraph("  ------------------------------------------Cantidad-----------imp. Unit--------Dev----------Deducc")).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT).setPaddingLeft(15);;
nomina1.add(new Paragraph("  salario base:"+"                              30 dias    "+"            "+trabajador.getCalculo().calculoMes()+"            "+trabajador.getCalculo().calculoBase())).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  prorata:"+"                                      30 dias "+"               "+trabajador.getCalculo().prorataMes()+"               "+trabajador.getCalculo().calculoProrateo())).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  complemento:"+"                            30 dias"+"               "+trabajador.getCalculo().complementoMes()+"               "+trabajador.getCalculo().calculoComplemento())).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Antiguedad:                               "+trabajador.getCalculo().calculoTrienio()+" Trienios"+"            "+trabajador.getCalculo().AntiguedadMes()+"               "+trabajador.getCalculo().calculoAntiguedad())).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Contingencias generales:           4,7%"+"               de "+trabajador.getCalculo().getCalculoEmpresarioBase()+"                             "+trabajador.getNomina().getSeguridadSocialTrabajador())).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Desempleo:                                1,6%"+"               de "+trabajador.getCalculo().getCalculoEmpresarioBase()+"                             "+trabajador.getNomina().getDesempleoTrabajador())).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  Cuota de formacion:                   0,1%"+"               de "+trabajador.getCalculo().getCalculoEmpresarioBase()+"                             "+trabajador.getNomina().getFormacionTrabajador())).setTextAlignment(TextAlignment.LEFT);;
//nomina1.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina1.add(new Paragraph("  IRPF:                                       12.13%"+"              de "+trabajador.getCalculo().getCalculoBaseIRPF()+"                             "+trabajador.getCalculo().calculoIRFP())).setTextAlignment(TextAlignment.LEFT);;
tabla5.addCell(nomina1);

Cell nomina2 = new Cell();
nomina2.setBorder(new SolidBorder(1));
nomina2.setWidth(500);

nomina2.add(new Paragraph("  Total Deducciones                                                                                              "+trabajador.getDeducciones())).setTextAlignment(TextAlignment.LEFT);;
nomina2.add(new Paragraph("  Total Devengos                                                                             "+trabajador.getDevengos())).setTextAlignment(TextAlignment.LEFT);;
tabla6.addCell(nomina2);

Cell nomina3 = new Cell();
nomina3.setBorder(new SolidBorder(1));
nomina3.add(new Paragraph("Liquido a percibir            "+((double)Math.round(trabajador.getNomina().getLiquidoNomina() * 100d)/100d))).setTextAlignment(TextAlignment.CENTER);;
tabla7.addCell(nomina3);

Cell nomina4 = new Cell();
nomina4.setBorder(new SolidBorder(1));
nomina4.add(new Paragraph("Calculo empresaio: BASE                                                                      "+trabajador.getNomina().getBaseEmpresario())).setTextAlignment(TextAlignment.LEFT);;

nomina4.add(new Paragraph("                     \n")).setTextAlignment(TextAlignment.LEFT);;
nomina4.add(new Paragraph("TOTAL empresaio                                                                                   "+trabajador.getCosteTotalParaEmpresario())).setTextAlignment(TextAlignment.LEFT);;
nomina4.add(new Paragraph("Coste total trabajador                                                                              "+trabajador.getCosteTotalTrabajador())).setTextAlignment(TextAlignment.LEFT);;

tabla8.addCell(nomina4);

empty.add(tabla2);//cabecera
empty.add(tabla3);//separador
empty.add(empresa);
empty.add(empleado);//cabecera empleado
empty.add(tabla1);//informacion empleado
empty.add(empleado1);
empty.add(tabla3);//separador
empty.add(tabla4);//cabecera nomina
empty.add(tabla5);
empty.add(tabla6);
empty.add(tabla7);
empty.add(tabla3);//separador
empty.add(tabla8);

doc.add(empty);
doc.close();
    
}
    
}
