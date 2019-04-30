/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Date;
import modelo.*;


public class CalculoTrabajador {
    private Parametro parametro;
    private Trabajadorbbdd Trabajador;
    
    public CalculoTrabajador(Parametro parametro,Trabajadorbbdd Trabajador){
    this.parametro=parametro;
    this.Trabajador=Trabajador;
    }
//metodo que devuelve el salario base en bruto mensual
   Double calculoBase(int index){
        ArrayList brutos = parametro.getSalariobase();
        Double bruto=  (Double) brutos.get(index);
        return bruto/14;
    }
 //metodo para obtener el index para operar dependiendo de la categoria del trbaajador   
    int calculoIndex(){
        int index=0;
        Double bruto=0.0;
        String categoria= Trabajador.getCategorias().getNombreCategoria();
        if(parametro.getCategorias().contains(categoria)){//para comprobar que existe la categoria
            index =parametro.getCategorias().indexOf(categoria);
        }else{
            System.out.println("error al leer la categoria");
        }
   return index;
    }
    
    Double calculoComplemento(){
        Double complemento = parametro.getComplementos().get(calculoIndex())/14;
        return complemento;
    }
    Double calculoAntiguedad(Trabajadorbbdd trabajador){
        //Extraemos el año y obtenemos los trienios
        Date alta = trabajador.getFechaAlta();
        int yearNomina=2019;
        String year = alta.toString().substring(alta.toString().length()-4);
        int aux = Integer.parseInt(year);
        int trienios = (yearNomina-aux)/3;
        Double antiguedad = parametro.getTrienio().get(trienios-1);;
        return antiguedad;
    }
    
    Double calculoProrateo(Trabajadorbbdd trabajador){
        Double resultado=0.0;
        
    if(trabajador.getProrateo().equals("SI")){
        //calculo salario base y complementp
        Double complemento = parametro.getComplementos().get(calculoIndex())/14;
        Double salarioBase= calculoBase(calculoIndex());//dado por el index de categoria
        Double antiguedad = calculoAntiguedad(trabajador);
        resultado=(salarioBase+complemento+antiguedad)*2/12;
    }
    return resultado;
    }
}