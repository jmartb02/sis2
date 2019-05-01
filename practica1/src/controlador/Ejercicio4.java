/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import modelo.*;
import vista.*;


public class Ejercicio4 {
    private ArrayList<Trabajadorbbdd> listaTrabajadores;
    
    public Ejercicio4(ArrayList<Trabajadorbbdd> listaTrabajadores, Parametro parametro) throws ParseException{
        this.listaTrabajadores = listaTrabajadores;
        Scanner scanner = new Scanner(System.in);
        Ventana ventana = new Ventana();
        ventana.imprimirPorPantalla("Inserte una fecha:\n");
        String fecha = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(fecha);
        
        for(Trabajadorbbdd trab: listaTrabajadores){
            if(date.before(trab.getFechaAlta())){
               System.out.println("\n\n");
               CalculoUnTrabajador cal = new CalculoUnTrabajador(trab,parametro, date);
               cal.run();
               System.out.println("\n\n");

            }

        }
    }
          
        
}
