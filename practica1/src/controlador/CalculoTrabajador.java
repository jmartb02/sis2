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
    
private  Parametro parametro;
    private  Trabajadorbbdd trabajador;
    private double complemento;
    
    public CalculoTrabajador(Parametro parametro,Trabajadorbbdd trabajador){
    this.parametro=parametro;
    this.trabajador=trabajador;
    }
    Double calculoComplemento(){
        Double complemento = parametro.getComplementos().get(calculoIndex())/14;
        return complemento;
}
    
//metodo que devuelve el salario base en bruto mensual
   Double calculoBase(){
       int index = calculoIndex();
        ArrayList brutos = parametro.getSalariobase();
        Double bruto=  (Double) brutos.get(index);
        return bruto/14;
    }
 //metodo para obtener el index para operar dependiendo de la categoria del trbaajador   
    int calculoIndex(){
        int index=0;
        Double bruto=0.0;
        String categoria= trabajador.getCategorias().getNombreCategoria();
        if(parametro.getCategorias().contains(categoria)){//para comprobar que existe la categoria
            index =parametro.getCategorias().indexOf(categoria);
        }else{
            System.out.println("error al leer la categoria");
        }
   return index;
    }
    
   /* public void  calculoComplemento(){
         complemento = parametro.getComplementos().get(calculoIndex())/14;
        System.out.println(complemento);
    }
    public Double getcomplemento() {
        return this.complemento;
    }*/
    Double calculoAntiguedad(){
        //Extraemos el año y obtenemos los trienios
        Date alta = trabajador.getFechaAlta();
        Double antiguedad =0.0;
        int yearNomina=2019;
        String year = alta.toString().substring(alta.toString().length()-4);
        int aux = Integer.parseInt(year);
        int trienios = (yearNomina-aux)/3;
        if(trienios>=1){
        antiguedad = parametro.getTrienio().get(trienios-1);;
        }
        return antiguedad;
    }
    
    Double calculoProrateo(){
        Double resultado=0.0;
        //hay que hacer un if a la hora de mostrarlo, lo calculamos en todos los casos
       //porque es necesario para los calculos de contingencias y demas
    //if(trabajador.getProrateo().equals("SI")){
        Double complemento = parametro.getComplementos().get(calculoIndex())/14;
        Double salarioBase= calculoBase();//dado por el index de categoria
        Double antiguedad = calculoAntiguedad();
        resultado=(salarioBase+complemento+antiguedad)*2/12;
   //}
    return resultado;
    }
    Double getContingencias(){
        Double contingencia = parametro.getCuota().get(0);
        return contingencia;
    }
    Double getDesempleo(){
        Double desempleo = parametro.getCuota().get(1);
        return desempleo;
    }
    Double getFormacion(){
        Double formacion = parametro.getCuota().get(2);
        return formacion;
    }
   Double calculoContingecias(){
    Double resultado = (calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento())*getContingencias()/100;
    return resultado;       
   }
   Double calculoDesempleo(){
    Double resultado = (calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento())*getDesempleo()/100;
    return resultado;       
   }
   Double calculoFormacion(){
    Double resultado = (calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento())*getFormacion()/100;
    return resultado;       
   }
   
}