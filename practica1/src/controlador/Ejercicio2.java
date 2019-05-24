/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Trabajadorbbdd;
import modelo.CrearXML;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Sheik
 */
public class Ejercicio2 {
        public Ejercicio2(){

        }
    public static void run(ArrayList<Trabajadorbbdd> listaTrabajadores) throws IOException, ParserConfigurationException{
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
        
       comprobar(listaTrabajadores, dnis);

       CrearXML XML = new CrearXML();
       XML.errorXML(listaTrabajadores);
    }
    
    
        public static void comprobar(ArrayList<Trabajadorbbdd> listaTrabajadores, int[] dnis) {
        String abecedario = "TRWAGMYFPDXBNJZSQVHLCKE";
        char[] letras = abecedario.toCharArray();
        int posicion = 0;
        for(Trabajadorbbdd trabajador: listaTrabajadores){
            if(dnis[posicion] < 24){
                if(!String.valueOf(letras[dnis[posicion]]).equals(trabajador.getNifnie().substring(trabajador.getNifnie().length()-1))){
                    trabajador.setNifnie(trabajador.getNifnie().subSequence(0, trabajador.getNifnie().length()-1).toString() + letras[dnis[posicion]]);
                }
            }
            
            posicion++;
        }
    }
    }

