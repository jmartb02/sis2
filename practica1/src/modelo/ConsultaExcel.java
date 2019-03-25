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
    
        public static void escribir(ArrayList<Trabajadorbbdd> trabajadores)throws FileNotFoundException, IOException{
        FileInputStream file;
        int aux = 2;
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
                 
                 if(trabajador.getIban() != ""){
                     cell = sheet.getRow(row).getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); //obtiene la fila y columna
                    cell.setCellValue(trabajador.getIban());//cambia la celda
                 }
                 
                 row++;
                 aux++;
             }
             
             file.close();
              try (FileOutputStream outFile = new FileOutputStream(new File(excel))) {
                        workbook.write(outFile);
                    }

         }
        }
}
