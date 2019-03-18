/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import java.io.IOException;
import java.util.ArrayList;
import vista.*;
import java.util.Scanner;
import modelo.*;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
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
    
    public static void ejercicio2() throws IOException{
        ArrayList<String> listaTrabajadores;
        ConsultaExcel consulta = new ConsultaExcel("src\\resources\\SistemasInformacionII.xlsx");
        listaTrabajadores = consulta.leer();
        int aux = 0;
        int[] dnis = new int[listaTrabajadores.size()];
        for(String pos: listaTrabajadores){
            String fraseAux = "";
            if( pos != ""){
              if(pos.subSequence(0, 1).equals("X")){
                fraseAux = "0";
                fraseAux += pos.subSequence(1, pos.length()-1);
            }else if(pos.subSequence(0, 1).equals("Y")){
                fraseAux = "1";
                fraseAux += pos.subSequence(1, pos.length()-1);
            }else if(pos.subSequence(0, 1).equals("Z")){
                fraseAux = "2";
                fraseAux += pos.subSequence(1, pos.length()-1);
            }else if(pos != ""){
                fraseAux += pos.subSequence(0, pos.length()-1);
            }  
            }
            
                System.out.println(fraseAux+ " "+aux);
                aux++;
        }
    }
    
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
}
