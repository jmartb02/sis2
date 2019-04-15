/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import modelo.Categorias;
import modelo.Empresas;
import modelo.Trabajadorbbdd;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ConsultaExcel {
    private static String excel;
    //private static List<String> listaTrabajadores;
    
    public ConsultaExcel(String excel){
        this.excel = excel;
     //   this.listaTrabajadores ;
    }
    
    public static ArrayList<Trabajadorbbdd> leer()throws FileNotFoundException, IOException{
        ArrayList<Trabajadorbbdd> listaTrabajadoresbbdd = new ArrayList<Trabajadorbbdd>();
       FileInputStream file;
        file = new FileInputStream(new File(excel));
        
        try ( // Crear el objeto que tendra el libro de Excel
                XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            /*
            * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
            */
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            // Recorremos todas las filas para mostrar el contenido de cada celda
            //metodo getCell
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Trabajadorbbdd trabajador = new Trabajadorbbdd();
                row = rowIterator.next();
          
                if(row.getCell(0) != null){
                    trabajador.setNombre(row.getCell(0).getStringCellValue());
                }else{
                    trabajador.setNifnie("");                    
                }
                
                if(row.getCell(1) != null){
                    trabajador.setApellido1(row.getCell(1).getStringCellValue());
                }else{
                    trabajador.setApellido1("");                    
                }
                
                if(row.getCell(2) != null){
                    trabajador.setApellido2(row.getCell(2).getStringCellValue());
                }else{
                    trabajador.setApellido2("");                    
                }
                
                if(row.getCell(3) != null){
                    trabajador.setNifnie(row.getCell(3).getStringCellValue());
                }else{
                    trabajador.setNifnie("");                    
                }
                if(row.getCell(6) != null){
                    Empresas empresa = new Empresas();
                    empresa.setNombre(row.getCell(6).getStringCellValue());
                    trabajador.setEmpresas(empresa);
                }else{
                    Empresas empresa = new Empresas();
                    empresa.setNombre("");
                    trabajador.setEmpresas(empresa);                  
                }
                
                if(row.getCell(7) != null){
                    Categorias categoria= new Categorias();
                    categoria.setNombreCategoria(row.getCell(7).getStringCellValue());
                    trabajador.setCategorias(categoria);
                }else{
                    Categorias categoria= new Categorias();
                    categoria.setNombreCategoria("");
                    trabajador.setCategorias(categoria);                
                }
                
                if(row.getCell(10) != null){
                    trabajador.setCodigoCuenta(row.getCell(10).getStringCellValue());
                }else{
                    trabajador.setCodigoCuenta("");
                }
                
                if(row.getCell(11) != null){
                    trabajador.setPaisOrigen(row.getCell(11).getStringCellValue());
                }else{
                    trabajador.setPaisOrigen("");
                }
                
                listaTrabajadoresbbdd.add(trabajador);


            }
            // cerramos el libro excel
        }
        
        return listaTrabajadoresbbdd;
    }
    
   public Parametro leer1() throws FileNotFoundException, IOException{
       Parametro listanominas = new Parametro();
       ArrayList<String> categorias  = new ArrayList<String>();
       ArrayList<Double> salarios = new ArrayList<Double>();
       ArrayList<Double> complementos = new ArrayList<Double>();
       ArrayList<Double> brutos = new ArrayList<Double>();
       ArrayList<Double> retenciones = new ArrayList<Double>();
       ArrayList<Double> trienios = new ArrayList<Double>();
       ArrayList<Double> cuotas = new ArrayList<Double>();
       FileInputStream file;
       file = new FileInputStream(new File(excel));
        try( XSSFWorkbook workbook = new XSSFWorkbook(file)){

             XSSFSheet sheet1 = workbook.getSheetAt(1);
             Iterator<Row> rowIterator = sheet1.iterator();
             Row row;
             rowIterator.next();

    for(int i=0;i<49;i++){
       row = rowIterator.next();
       double bruto= row.getCell(5).getNumericCellValue();
       brutos.add(bruto);
       double retencion= row.getCell(6).getNumericCellValue();
       retenciones.add(retencion);
       
       if(i>16&&i<35){//obtener trienios
        double trienio= row.getCell(3).getNumericCellValue();
        trienios.add(trienio);
       }
       if(i<14){//lectura de columnas de 14
        String categoria=row.getCell(0).getStringCellValue();
        categorias.add(categoria);
        double salario= row.getCell(1).getNumericCellValue();
        salarios.add(salario);
        double complemento = row.getCell(2).getNumericCellValue();
        complementos.add(complemento);
       }
       if(i>15&&i<24){//obtener cuotas extra
        double cuota = row.getCell(1).getNumericCellValue();
        cuotas.add(cuota);
       }
       
   }
   listanominas.setSalariobase(salarios);
   listanominas.setCategorias(categorias);
   listanominas.setComplementos(complementos);
   listanominas.setTrienio(trienios);
   listanominas.setBruto(brutos);
   listanominas.setRetenciones(retenciones);
   listanominas.setCuota(cuotas);
   
   System.out.print(listanominas.getCategorias());

  return  listanominas;
     }
   }
 
      
        public static void escribir(ArrayList<Trabajadorbbdd> trabajadores)throws FileNotFoundException, IOException{
        FileInputStream file;
        file = new FileInputStream(new File(excel));
        try ( // Crear el objeto que tendra el libro de Excel
            XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            
        
             /*
             * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
             */
             XSSFSheet sheet = workbook.getSheetAt(0);
             Iterator<Row> rowIterator = sheet.iterator();
             int row = 1;
             Cell cell;
             // Recorremos todas las filas para mostrar el contenido de cada celda
             //metodo getCell
             rowIterator.next();
             
             
             for(Trabajadorbbdd trabajador: trabajadores){
                 
                 if(trabajador.getNifnie() != ""){
                     cell = sheet.getRow(row).getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); //obtiene la fila y columna
                    cell.setCellValue(trabajador.getNifnie());//cambia la celda
                 }
                 if(trabajador.getEmail() != ""){
                     cell = sheet.getRow(row).getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); //obtiene la fila y columna
                    cell.setCellValue(trabajador.getEmail());//cambia la celda
                 }
                 
                 if(trabajador.getIban() != ""){
                     cell = sheet.getRow(row).getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); //obtiene la fila y columna
                    cell.setCellValue(trabajador.getIban());//cambia la celda
                 }
                 
                 if(trabajador.getCodigoCuenta() != ""){
                     cell = sheet.getRow(row).getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); //obtiene la fila y columna
                    cell.setCellValue(trabajador.getCodigoCuenta());//cambia la celda
                 }
                 
                 row++;
             }
             
             file.close();
              try (FileOutputStream outFile = new FileOutputStream(new File(excel))) {
                        workbook.write(outFile);
                    }

         }
        }
}