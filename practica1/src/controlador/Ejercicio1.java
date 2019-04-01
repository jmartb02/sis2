/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.util.Scanner;
import vista.*;
import modelo.*;
/**
 *
 * @author Sheik
 */
public class Ejercicio1 {
    
    public Ejercicio1(){
        
    }
    
    public static void run(){
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

        
    
}
