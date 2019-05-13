/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.Parametro;
import Modelo.Trabajadorbbdd;
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
    
    public Double calculoProrateo(){
        Double resultado=0.0;
        //hay que hacer un if a la hora de mostrarlo, lo calculamos en todos los casos
       //porque es necesario para los calculos de contingencias y demas
        if(trabajador.getProrateo().equals("SI")){
            Double complemento = parametro.getComplementos().get(calculoIndex())/14;
            Double salarioBase= calculoBase();//dado por el index de categoria
            
            Date alta = trabajador.getFechaAlta();
            LocalDate localDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate altaTrabajador = alta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int yearNomina=localDate.getYear();
            int yearAltaTrabajador = altaTrabajador.getYear();
            int trienios = (yearNomina-yearAltaTrabajador)/3;
            
            Double antiguedad = 0.0;
            
            if(trienios>=1){
                antiguedad = parametro.getTrienio().get(trienios-1);
            }

            double aux = salarioBase+redondear(complemento)+antiguedad;
            aux = aux/6;
            resultado=(salarioBase+redondear(complemento)+calculoAntiguedad())/6;
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
        double resultado = calculoBase()+calculoAntiguedad()+calculoComplemento();
        System.out.println(resultado);
        resultado += calculoBase()/6 + calculoComplemento()/6; 
        
        int index = calculoIndex();    
        Date alta = trabajador.getFechaAlta();
        LocalDate localDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate trabajadorDate = alta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearNomina=localDate.getYear();
        int year = trabajadorDate.getYear();
        int trienios = (yearNomina-year)/3;
        if((yearNomina-year)%3 == 0 && trienios > 0){
            resultado += parametro.getTrienio().get(trienios-1)/6;
        }else{
            resultado += calculoAntiguedad()/6;
        }
        
            return redondear(resultado);
          // return redondear(((calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento())*14)/12);
       }
       

   }
   
   public Double getBrutoAnual(){
        double brutoAnual = 0;
        LocalDate localDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date alta = trabajador.getFechaAlta();
        LocalDate altaTrabajador = alta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int index = calculoIndex();
        Double brutos = parametro.getSalariobase().get(index);
        Double complementos = parametro.getComplementos().get(index);
        double mensual = (brutos+complementos)/14;
        int yearNomina=localDate.getYear();
        int yearAlta = altaTrabajador.getYear();
        int tiempoTrabajador = (yearNomina - yearAlta);   
        int trienios = tiempoTrabajador/3; 
        System.out.println("Trabajado "+tiempoTrabajador+" "+trienios);
        
        if(tiempoTrabajador % 3 == 0){
            if(trienios > 1){
                brutoAnual += altaTrabajador.getMonthValue()*(mensual+parametro.getTrienio().get(trienios-2))+
                        (12-altaTrabajador.getMonthValue())*(mensual+parametro.getTrienio().get(trienios-1));
            }else if(trienios > 0){
                brutoAnual += altaTrabajador.getMonthValue()*(mensual)+
                        (12-altaTrabajador.getMonthValue())*(mensual+parametro.getTrienio().get(trienios-1));
            }else{
                brutoAnual += 12*mensual;
            }
        }else{
            if(trienios > 0){
                System.out.println("ashdfiohof");
                brutoAnual += 12*(mensual+parametro.getTrienio().get(trienios-1));
            }
        }
        System.out.println("Brutoasdfasdf "+brutoAnual);

        
        if(trabajador.getProrateo().equals("SI")){
            if(tiempoTrabajador % 3 == 0){
                if(altaTrabajador.getMonthValue() > 6){
                    if(trienios > 1)
                        brutoAnual += 7*(mensual+parametro.getTrienio().get(trienios-2))/6;
                    else
                        brutoAnual += 7*(mensual)/6;  
                }else{
                    if(trienios > 0)
                        brutoAnual += 7*(mensual+parametro.getTrienio().get(trienios-1))/6;
                    else
                        brutoAnual += 7*(mensual)/6; 
                }
                
                tiempoTrabajador++;
                trienios = tiempoTrabajador/3; 
                if(altaTrabajador.getMonthValue() > 6){
                    if(trienios > 1)
                        brutoAnual += 7*(mensual+parametro.getTrienio().get(trienios-2))/6;
                    else
                        brutoAnual += 7*(mensual)/6;  
                }else{
                    if(trienios > 0)
                        brutoAnual += 7*(mensual+parametro.getTrienio().get(trienios-1))/6;
                    else
                        brutoAnual += 7*(mensual)/6; 
                }
                    
            }else{
                if(trienios > 0){
                    brutoAnual += 2*(mensual +parametro.getTrienio().get(trienios-1));
                }else{
                    brutoAnual += 2*mensual;
                }                
            }
        }else{
            
            if(tiempoTrabajador % 3 != 0){
                if(trienios > 0)
                    brutoAnual += (mensual+parametro.getTrienio().get(trienios-1));
                else
                    brutoAnual += mensual;
            }else{
                if(altaTrabajador.getMonthValue() > 5){                    
                    if(trienios > 1){
                        brutoAnual += (mensual+parametro.getTrienio().get(trienios-2)); 
                    }                       
                    else
                        brutoAnual += mensual;
                }else{
                    if(trienios > 0)
                        brutoAnual += (mensual+parametro.getTrienio().get(trienios-1));
                    else
                        brutoAnual += mensual;
                }  
            }
            if(tiempoTrabajador % 3 == 0){
                tiempoTrabajador++;
                trienios = tiempoTrabajador/3;
            }
            
            if(tiempoTrabajador % 3 != 0){
                if(trienios > 0)
                    brutoAnual += (mensual+parametro.getTrienio().get(trienios-1));
                else
                    brutoAnual += mensual;
                                    
            }else{
                if(altaTrabajador.getMonthValue() > 6){
                    if(trienios > 0)
                        brutoAnual += (mensual+parametro.getTrienio().get(trienios-1)); 
                    else
                        brutoAnual += mensual;
                }else{
                    if(trienios > 1)
                        brutoAnual += (mensual+parametro.getTrienio().get(trienios-2));
                    else
                        brutoAnual += mensual;
                }  
            }
        }

        
        return redondear(brutoAnual);
   }
   
   /*public Double getBrutoAnual(){
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
        System.out.println(trabajador.getProrateo()+ " "+trienios);
        if(trabajador.getProrateo().equals("SI") && trienios > 1){
            if(tiempoTrabajador%3 == 2){
                double n = (brutos + complementos)/14;
                brutoAnual = 0;
                brutoAnual += 12*(n+parametro.getTrienio().get(trienios-1));
                brutoAnual += 11*((n+parametro.getTrienio().get(trienios-1))/6);
                brutoAnual += 1*((n+parametro.getTrienio().get(trienios))/6);
                //brutoAnual += 12*((n+parametro.getTrienio().get(trienios-1))/6);
            }else if(tiempoTrabajador%3 != 0 ){
                double n = (brutos + complementos)/14;
                brutoAnual = 0;
                brutoAnual += 12*(n+parametro.getTrienio().get(trienios-1));
                brutoAnual += 12*((n+parametro.getTrienio().get(trienios-1))/6);

            }else{
                System.out.println("BLAS");
                double n = (brutos + complementos)/14;
                brutoAnual = 0;
                brutoAnual += (1+altaTrabajador.getMonthValue())*(n+parametro.getTrienio().get(trienios-2));
                brutoAnual += (12 - (altaTrabajador.getMonthValue()+1))*(n+parametro.getTrienio().get(trienios-1));
                brutoAnual += 6*((n+parametro.getTrienio().get(trienios-1))/6);
                brutoAnual += 6*((n+parametro.getTrienio().get(trienios-2))/6);
            }
        }else{
            System.out.println("AWQUI "+tiempoTrabajador);
            if((tiempoTrabajador%3) != 0 || trienios == 0){  
            
                if(trienios > 0){
                
                    brutoAnual += 14*parametro.getTrienio().get(trienios-1);
                }           
            }else{
                if(trienios == 1){
                    brutoAnual += (12-altaTrabajador.getMonthValue())*parametro.getTrienio().get(trienios-1);

                }else{
                    brutoAnual += altaTrabajador.getMonthValue()*parametro.getTrienio().get(trienios-2)+ (12-altaTrabajador.getMonthValue())*parametro.getTrienio().get(trienios-1);
                    System.out.println("Brtuo "+brutoAnual);
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
        }
        
        
        
       
       
        return redondear(brutoAnual);
   }*/
   
   
   public Double getCalculoBaseIRPF(){
       Double resultado = calculoBase()+ calculoProrateo()+calculoAntiguedad()+calculoComplemento();
       return redondear(resultado);
   }
   
}