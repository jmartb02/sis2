/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Parametro;
import modelo.Trabajadorbbdd;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class CalculoTrabajador {
    
    private  Parametro parametro;
    private  Trabajadorbbdd trabajador;
    private double complemento;
    private Date fecha;
    private boolean esExtra;
    
    public CalculoTrabajador(Parametro parametro,Trabajadorbbdd trabajador, Date fecha, boolean esExtra){
    this.parametro=parametro;
    this.trabajador=trabajador;
    this.fecha = fecha;
    this.esExtra = esExtra;
    }
    public Double calculoComplemento(){
        Double complemento = parametro.getComplementos().get(calculoIndex())/14;
        return ((double)Math.round(complemento * 100d)/100d);
    }
    public Double redondear(double numero){
        return ((double)Math.round(numero * 100d)/100d);
    }
    
//metodo que devuelve el salario base en bruto mensual
   public Double calculoBase(){
       int index = calculoIndex();
        ArrayList brutos = parametro.getSalariobase();
        Double bruto=  (Double) brutos.get(index);
        return redondear(bruto/14);
        
    }
   public Double calculoMes(){
   Double tmp= calculoBase();
   tmp=tmp/30;
   return redondear(tmp);
   }
   public Double prorataMes(){
   Double tmp= calculoProrateo()/30;
   return redondear(tmp);
   }
   public Double complementoMes(){
   Double tmp= calculoComplemento()/30;
   return redondear(tmp);
   }
    public Double AntiguedadMes(){
   Double tmp= calculoAntiguedad()/30;
   return redondear(tmp);
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
    

    public Double calculoAntiguedad(){
        //Extraemos el año y obtenemos los trienios
        Date alta = trabajador.getFechaAlta();
        Double antiguedad =0.0;
        LocalDate localDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate trabajadorDate = alta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearNomina=localDate.getYear();
        int year = trabajadorDate.getYear();
        int trienios = (yearNomina-year)/3;
        
        int mesNomina = localDate.getMonthValue();
        int mes = trabajadorDate.getMonthValue();
        if((yearNomina-year) % 3 == 0 && mesNomina <= mes){
            trienios--;
        }
            
        if(trienios>=1){
        antiguedad = parametro.getTrienio().get(trienios-1);
        }
        return antiguedad;
    }
        public int calculoTrienio(){
        //Extraemos el año y obtenemos los trienios
        Date alta = trabajador.getFechaAlta();
        Double antiguedad =0.0;
        LocalDate localDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate trabajadorDate = alta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearNomina=localDate.getYear();
        int year = trabajadorDate.getYear();
        int trienios = (yearNomina-year)/3;
        
        int mesNomina = localDate.getMonthValue();
        int mes = trabajadorDate.getMonthValue();
        if((yearNomina-year) % 3 == 0 && mesNomina <= mes){
            trienios--;
        }
            
   
        return trienios;
    }
    
    public Double calculoProrateo(){
        Double resultado=0.0;
        //hay que hacer un if a la hora de mostrarlo, lo calculamos en todos los casos
       //porque es necesario para los calculos de contingencias y demas
        if(trabajador.getProrateo().equals("SI")){
            Double complemento = parametro.getComplementos().get(calculoIndex())/14;
            Double salarioBase= calculoBase();//dado por el index de categoria
            Double antiguedad = calculoAntiguedad();
            resultado=(salarioBase+redondear(complemento)+antiguedad)/6;
        }
    return redondear(resultado);
    }
    public Double getContingencias(){
        Double contingencia = parametro.getCuota().get(0);
        return contingencia;
    }
    public Double getDesempleo(){
        Double desempleo = parametro.getCuota().get(1);
        return desempleo;
    }
    public Double getFormacion(){
        Double formacion = parametro.getCuota().get(2);
        return formacion;
    }
    public Double getContingenciasEmpresario(){
        return parametro.getCuota().get(3);
    }
    public Double getFogasa(){
        return parametro.getCuota().get(4);
    }
    public Double getDesempleoEmpresario(){
        return parametro.getCuota().get(5);
    }
    public Double getFormacionEmpresaio(){
        return parametro.getCuota().get(6);
    }
    public Double getAccidentesEmpresario(){
        return parametro.getCuota().get(7);
    }
    public Double getIRPF(){
        double bruto = (getCalculoEmpresarioBase()*12)-12000.0;
        int posicion = 0;
        while (bruto > 0){
            bruto -= 1000;
            posicion++;
        }
        return parametro.getRetenciones().get(posicion);
    }
   public Double calculoContingecias(){
       Double resultado = 0.0;
       if(!esExtra){
          resultado = (getCalculoEmpresarioBase())*getContingencias()/100; 
       }
    return redondear(resultado);       
   }
   public Double calculoDesempleo(){
        Double resultado = 0.0;
       if(!esExtra){
            resultado = (getCalculoEmpresarioBase())*getDesempleo()/100;
       }
    return redondear(resultado);        
   }
   public Double calculoFormacion(){
       Double resultado = 0.0;
       if(!esExtra){
            resultado = (getCalculoEmpresarioBase())*getFormacion()/100;
       }
    return redondear(resultado);         
   }
   
   public Double calculoIRFP(){
       Double resultado = (getCalculoBaseIRPF()*getIRPF()/100);
       return redondear(resultado);  
   }
   public Double calculoContigenciasComunes(){
       Double resultado = 0.0;
       if(!esExtra){
        resultado = (getCalculoEmpresarioBase()*getContingenciasEmpresario()/100);
       }
       return redondear(resultado);  
   }
   public Double calculoDesempleoEmpresario(){
       Double resultado = 0.0;
       if(!esExtra){
            resultado = (getCalculoEmpresarioBase()*getDesempleoEmpresario()/100);
       }    
       return redondear(resultado);
   }
   public Double calculoFormacionEmpresario(){
       Double resultado = 0.0;
       if(!esExtra){
            resultado = (getCalculoEmpresarioBase()*getFormacionEmpresaio()/100);
       }
       return redondear(resultado);
   }
   public Double calculoAccidentesEmpresario(){
       Double resultado = 0.0;
       if(!esExtra){
            resultado = (getCalculoEmpresarioBase()*getAccidentesEmpresario()/100);
       }
       return redondear(resultado);
   }
   public Double calculoFogasa(){
       Double resultado = 0.0;
       if(!esExtra){
            resultado = (getCalculoEmpresarioBase()*getFogasa()/100);
       }
       return redondear(resultado);
   }
   
   public Double getCalculoEmpresarioBase(){
       if(trabajador.getProrateo().equals("SI")){
           return redondear(calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento());
       }else{
           return redondear(((calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento())*14)/12);
       }
       

   }
   
   public Double getBrutoAnual(){
       LocalDate localDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       Date alta = trabajador.getFechaAlta();
       LocalDate altaTrabajador = alta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       int index = calculoIndex();
            Double brutos = parametro.getSalariobase().get(index);
            Double complementos = parametro.getComplementos().get(index);
       double brutoAnual = brutos+complementos;
        int yearNomina=localDate.getYear();
        int yearAlta = altaTrabajador.getYear();
        int tiempoTrabajador = (yearNomina - yearAlta);   
        int trienios = tiempoTrabajador/3; 
        
        if((tiempoTrabajador%3) != 0 || trienios == 0){  
            
            if(trienios > 0){
                
                brutoAnual += 14*parametro.getTrienio().get(trienios-1);
            }           
        }else{
            if(trienios == 1){
                brutoAnual += (12-altaTrabajador.getMonthValue())*parametro.getTrienio().get(trienios-1);

            }else{
                brutoAnual += altaTrabajador.getMonthValue()*parametro.getTrienio().get(trienios-2)+ (12-altaTrabajador.getMonthValue())*parametro.getTrienio().get(trienios-1);
            }       
            if(altaTrabajador.getMonthValue() > 6){
                
                if(trienios == 1){
                    brutoAnual += parametro.getTrienio().get(trienios-1);
                }else{
                    brutoAnual += parametro.getTrienio().get(trienios-1) + parametro.getTrienio().get(trienios-2);
                }
            }else{
               brutoAnual += 2*parametro.getTrienio().get(trienios-1); 
            }
        }
       
        return redondear(brutoAnual);
   }
   
   
   public Double getCalculoBaseIRPF(){
       Double resultado = calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento();
       return redondear(resultado);
   }
   
}