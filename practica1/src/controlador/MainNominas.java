/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.Parametro;
import Modelo.Trabajadorbbdd;
import Modelo.ConsultaExcel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException, FileNotFoundException, ParseException {
        ArrayList<Trabajadorbbdd> listaTrabajadores ;
        ConsultaExcel consulta = new ConsultaExcel("src/resources/SistemasInformacionII.xlsx");
        listaTrabajadores= consulta.leer();
        Parametro parametro = consulta.leer1();

        Ejercicio1 ejercicio1 = new Ejercicio1();
        Ejercicio2 ejercicio2 = new Ejercicio2();
        Ejercicio3 ejercicio3 = new Ejercicio3();
        ejercicio1.run();
        ejercicio2.run(listaTrabajadores);
        ejercicio3.run(listaTrabajadores);
        consulta.escribir(listaTrabajadores);
        
        Ejercicio4 ejercicio4 = new Ejercicio4(listaTrabajadores,parametro);
        
    }
    

    


    
}
