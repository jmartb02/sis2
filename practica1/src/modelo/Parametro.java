/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author cao
 */
public class Parametro {

  private  ArrayList <String> categorias;
  private  ArrayList <Double> salariobase,complementos,bruto,retenciones,trienio,cuota;
  
  
      public ArrayList <Double> getCuota() {
        return cuota;
    }

    public void setCuota(ArrayList <Double> cuota) {
        this.cuota = cuota;
    }
      public ArrayList <Double> getTrienio() {
        return trienio;
    }

    public void setTrienio(ArrayList <Double> trienio) {
        this.trienio = trienio;
    }
    public ArrayList <Double> getBruto() {
        return bruto;
    }

    public void setBruto(ArrayList <Double> bruto) {
        this.bruto = bruto;
    }

    public ArrayList <Double> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(ArrayList <Double> retenciones) {
        this.retenciones = retenciones;
    }
 
    public ArrayList <Double> getSalariobase() {
        return salariobase;
    }

    public void setSalariobase(ArrayList <Double> salariobase) {
        this.salariobase = salariobase;
    }


    public ArrayList <Double> getComplementos() {
        return complementos;
    }


    public void setComplementos(ArrayList <Double> complementos) {
        this.complementos = complementos;
    }
 
    public ArrayList<String> getCategorias(){
        return this.categorias;
        
    }
    public void setCategorias(ArrayList<String> categorias){
        this.categorias=categorias;
    }
}
