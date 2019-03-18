/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
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
    
    public static ArrayList<String> leer()throws FileNotFoundException, IOException{
        ArrayList<String> listaTrabajadores = new ArrayList<String>();
       FileInputStream file;
        file = new FileInputStream(new File(excel));
        int aux= 2;
        
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
                row = rowIterator.next();
 
                if(row.getCell(3) != null){
                    listaTrabajadores.add(row.getCell(3).getStringCellValue());
                }else{
                    listaTrabajadores.add("");

                }
                

                
               /* while (cellIterator.hasNext()){
                    celda = cellIterator.next();
                   if(celda.getRowIndex() >= 1 && celda.getColumnIndex() == 3){
                       
                        // Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...
                       /* switch(celda.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                if( DateUtil.isCellDateFormatted(celda) ){
                                    System.out.println(celda.getDateCellValue());
                                }else{
                                    System.out.println(celda.getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                System.out.println(celda.getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                System.out.println(celda.getBooleanCellValue());
                                break;
                        }*/

                      /* System.out.print(celda.getStringCellValue());
                       System.out.println(" "+aux);
                       aux++;
                       listaTrabajadores.add(celda.getStringCellValue());
                      //se mete cada valor de los dni en un arraylist original de dnis
                      // dni.anadirDniArray(celda.getStringCellValue());
                       //se mete la posicion el el arraylsit
                      // dni.anadirPosArray(celda.getRowIndex()+"-"+celda.getColumnIndex());
                   }
                }*/
            }
            // cerramos el libro excel
        }
        
        return listaTrabajadores;
    }
}
