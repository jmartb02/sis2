/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Trabajadorbbdd;
import java.util.ArrayList;

public class Limpiar {
    private ArrayList<Trabajadorbbdd> trabajadores;
    public Limpiar(ArrayList<Trabajadorbbdd> trabajadores){
        this.trabajadores = trabajadores;
    }
    
    public ArrayList<Trabajadorbbdd> limpiar(){
        ArrayList<Trabajadorbbdd> trabajadoresADevolver = new ArrayList<Trabajadorbbdd>();
        for(Trabajadorbbdd trab:this.trabajadores){
            boolean existe = false;
            for(Trabajadorbbdd trabajador:trabajadoresADevolver){
                if(trab.getNifnie() == trabajador.getNifnie() && trab.getNombre() == trabajador.getNombre() &&
                        trab.getApellido1() == trabajador.getApellido1() && trab.getApellido2() == trabajador.getApellido2()){
                    existe = true;
                }
            }
            if(!existe){
                trabajadoresADevolver.add(trab);
            }
        }
        return trabajadoresADevolver;
    }
}
