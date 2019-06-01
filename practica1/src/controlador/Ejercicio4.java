/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Trabajadorbbdd;
import modelo.Consulta;
import modelo.Parametro;
import modelo.Nomina;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import vista.*;


public class Ejercicio4 {
    private ArrayList<Trabajadorbbdd> listaTrabajadores;
    
    public Ejercicio4(ArrayList<Trabajadorbbdd> listaTrabajadores, Parametro parametro,Date date,String ruta) throws ParseException, IOException{
        this.listaTrabajadores = listaTrabajadores;
        Limpiar limpia = new Limpiar(listaTrabajadores);
        ArrayList<Trabajadorbbdd> trabajadoresFiltrados = limpia.limpiar();
        ArrayList<Nomina> nominas = new ArrayList<Nomina>();
   
        
        for(Trabajadorbbdd trab: trabajadoresFiltrados){
            if(date.after(trab.getFechaAlta())  && trab.getNifnie() != ""){
               System.out.println("\n\n");
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int month = localDate.getMonthValue();
                CalculoUnTrabajador cal = new CalculoUnTrabajador(trab,parametro, date, false, "");
                cal.run();
                createPdf pdf = new createPdf();
                    pdf.createPdf(cal,"",ruta);
                nominas.add(cal.getNomina());
                
                if(month == 6){
                    System.out.println("\n\n");
                    CalculoUnTrabajador calExtra = new CalculoUnTrabajador(trab,parametro, date, true, "JUNIO");
                    calExtra.run();  
                    nominas.add(calExtra.getNomina());
                    createPdf pdf2 = new createPdf();
                    pdf2.createPdf(calExtra,"EXTRA",ruta);
                }else if(month == 12){
                    System.out.println("\n\n");
                    CalculoUnTrabajador calExtra = new CalculoUnTrabajador(trab,parametro, date, true, "DICIEMBRE");
                    calExtra.run(); 
                    nominas.add(calExtra.getNomina());
                    createPdf pdf2 = new createPdf();
                    pdf2.createPdf(calExtra,"EXTRA",ruta);
                }

               System.out.println("\n\n");

            }

        }
        Limpiar limp = new Limpiar(listaTrabajadores);
        listaTrabajadores = limp.filtrar(listaTrabajadores);
        Consulta consulta = new Consulta();
        consulta.conectar();
        consulta.escribir(nominas);
        consulta.desconectar();
    }
          
        
}