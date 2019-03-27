/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.io.IOException;
/**
 *
 * @author cao
 */

public class createEmail {
       
    public static void calculo() throws IOException{
        
    ArrayList<Trabajadorbbdd> listaTrabajadores ;
    ArrayList<String> repetido = new ArrayList<>();
    ConsultaExcel consulta = new ConsultaExcel("src/resources/SistemasInformacionII.xlsx");
    listaTrabajadores= consulta.leer();
    
    String letraPA,letraSA,nombre,empresa, email;
    int aux= 0;

        for(Trabajadorbbdd trabajador: listaTrabajadores){
            if(trabajador.getEmail()==null){
                letraPA = trabajador.getApellido1().substring(0, 1).toLowerCase();
                nombre = trabajador.getNombre().substring(0,1).toLowerCase();
                empresa=trabajador.getEmpresas().getNombre().toLowerCase();
            
                if(trabajador.getApellido2()==""){
                    String aux2 = String.format("%02d", aux);
                    email = letraPA+ nombre+aux2+empresa+".es";
                        if(repetido.contains(email)){
                            aux ++;
                            String aux3 = String.format("%02d", aux);
                            email = letraPA+ nombre+aux3+empresa+".es";
                            aux=0;
                       }
                        repetido.add(email);
                        trabajador.setEmail(email);
                        ConsultaExcel.escribir(listaTrabajadores);
                        System.out.println(trabajador.getEmail());
                }
            if(trabajador.getApellido2()!=""){
                letraSA = trabajador.getApellido2().substring(0,1).toLowerCase();
                String aux2 = String.format("%02d", aux);
                email = letraPA+ letraSA+ nombre+aux2+empresa+".es";
                    if(repetido.contains(email)){
                        aux ++;
                        String aux3 = String.format("%02d", aux);
                        email = letraPA+ letraSA+ nombre+aux3+empresa+".es";
                        aux=0;
                    }
 
            repetido.add(email);
            trabajador.setEmail(email);
            ConsultaExcel.escribir(listaTrabajadores);
            System.out.println(trabajador.getEmail());
            }
            
        } //System.out.println("falla el if");

    }   
}    
}