/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        ArrayList<Trabajadorbbdd> listaTrabajadores ;
        ConsultaExcel consulta = new ConsultaExcel("src/resources/SistemasInformacionII.xlsx");
        listaTrabajadores= consulta.leer();
        
        Ejercicio1 ejercicio1 = new Ejercicio1();
        Ejercicio2 ejercicio2 = new Ejercicio2();
        Ejercicio3 ejercicio3 = new Ejercicio3();
        ejercicio1.run();
        ejercicio2.run(listaTrabajadores);
        ejercicio3.run(listaTrabajadores);
        
        consulta.escribir(listaTrabajadores);

    }
    

    


    
}
