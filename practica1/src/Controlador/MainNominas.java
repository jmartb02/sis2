/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import java.util.Scanner;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //DNI de prueba, Carolina 09741138V, IdCategoria 230, salario base por categoria 20000
         Consulta consulta = new Consulta();
         consulta.conectar();
         Scanner scanner = new Scanner(System.in);
         System.out.println("Ingrese un DNI");
         String DNI = scanner.nextLine();
         if(consulta.existe(DNI)){
             System.out.println(consulta.mostrar(DNI));
             consulta.aumentarSalarioBase(DNI);
             consulta.destruir(DNI);
             
         
         }else{
             System.out.println("No hemos encontrado el trabajador en nuestro sistema.");
         }
         
         consulta.desconectar();
         
    }
    
}
