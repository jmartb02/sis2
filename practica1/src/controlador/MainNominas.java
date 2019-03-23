/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.*;
import java.io.IOException;
import java.util.ArrayList;
import vista.*;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        ejercicio2();
    
    }
    
    public static void ejercicio1(){
        //DNI de prueba, Carolina 09741138V, IdCategoria 230, salario base por categoria 20000
        Ventana ventana = new Ventana();
         Consulta consulta = new Consulta();
         consulta.conectar();
         Scanner scanner = new Scanner(System.in);
         ventana.imprimirPorPantalla("Ingrese un DNI");
         String DNI = scanner.nextLine();
         if(consulta.existe(DNI)){
             ventana.imprimirPorPantalla(consulta.mostrar(DNI));
             ventana.imprimirPorPantalla(consulta.aumentarSalarioBase(DNI));
             ventana.imprimirPorPantalla(consulta.destruir(DNI));
             
         
         }else{
             ventana.imprimirPorPantalla("No hemos encontrado el trabajador en nuestro sistema.");
         }
         
         consulta.desconectar();
    }
    
    public static void ejercicio2() throws IOException, ParserConfigurationException{
        ArrayList<Trabajadorbbdd> listaTrabajadores;
        ConsultaExcel consulta = new ConsultaExcel("src\\resources\\SistemasInformacionII.xlsx");
        listaTrabajadores = consulta.leer();
        int aux = 0;
        int[] dnis = new int[listaTrabajadores.size()];
        for(Trabajadorbbdd trabajador: listaTrabajadores){
            String fraseAux = "";
            if( trabajador.getNifnie() != ""){
              if(trabajador.getNifnie().subSequence(0, 1).equals("X")){
                fraseAux = "0";
                fraseAux += trabajador.getNifnie().subSequence(1, trabajador.getNifnie().length()-1);
                }else if(trabajador.getNifnie().subSequence(0, 1).equals("Y")){
                    fraseAux = "1";
                    fraseAux += trabajador.getNifnie().subSequence(1, trabajador.getNifnie().length()-1);
                }else if(trabajador.getNifnie().subSequence(0, 1).equals("Z")){
                    fraseAux = "2";
                    fraseAux += trabajador.getNifnie().subSequence(1, trabajador.getNifnie().length()-1);
                }else if(trabajador.getNifnie() != ""){
                    fraseAux += trabajador.getNifnie().subSequence(0, trabajador.getNifnie().length()-1);
                }  
     
              dnis[aux] = Integer.parseInt(fraseAux) % 23; 
            }else{
                dnis[aux] = 24;
            }
            
            aux++;
        }
        
       if(!comprobar(listaTrabajadores, dnis)){
           //sustituir los DNI de la lista de trabajadores que hay almacenado en listaTrabajadores.
           consulta.escribir(listaTrabajadores);
       }
       
       CrearXML XML = new CrearXML();
       XML.errorXML(listaTrabajadores);
    }
    
    public static boolean comprobar(ArrayList<Trabajadorbbdd> listaTrabajadores, int[] dnis) {
        boolean correcto = true;
        String abecedario = "TRWAGMYFPDXBNJZSQVHLCKE";
        char[] letras = abecedario.toCharArray();
        int posicion = 0;
        for(Trabajadorbbdd trabajador: listaTrabajadores){
            if(dnis[posicion] < 24){
                if(!String.valueOf(letras[dnis[posicion]]).equals(trabajador.getNifnie().substring(trabajador.getNifnie().length()-1))){
                    correcto = false;
                    trabajador.setNifnie(trabajador.getNifnie().subSequence(0, trabajador.getNifnie().length()-1).toString() + letras[dnis[posicion]]);
                }
            }
            
            posicion++;
        }
       return correcto;
    }
    
}
