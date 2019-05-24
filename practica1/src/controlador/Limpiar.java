/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Empresas;
import modelo.Categorias;
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
    
    public ArrayList<Trabajadorbbdd> filtrar(ArrayList<Trabajadorbbdd> trabajador){
        ArrayList<Trabajadorbbdd> trabajadorADevolver = trabajador;
        ArrayList<Empresas> empresas = new ArrayList<Empresas>();
        ArrayList<Categorias> categorias = new ArrayList<Categorias>();
        for(Trabajadorbbdd trab:trabajadorADevolver){
            boolean existe = false;
            for(Empresas empresa:empresas){
                if(trab.getEmpresas().getCif() == empresa.getCif()){
                    existe = true;
                }
            }
            if(!existe){
                Empresas emp = new Empresas();
                emp.setCif(trab.getEmpresas().getCif());
                emp.setNombre(trab.getEmpresas().getNombre());
                empresas.add(emp);
            }
            existe = false;
            for(Categorias categoria:categorias){
                if(trab.getCategorias().getNombreCategoria() == categoria.getNombreCategoria()){
                    existe = true;
                }
            }
            if(!existe){
                Categorias cat = new Categorias();
                cat.setComplementoCategoria(trab.getCategorias().getComplementoCategoria());
                cat.setNombreCategoria(trab.getCategorias().getNombreCategoria());
                cat.setSalarioBaseCategoria(trab.getCategorias().getSalarioBaseCategoria());
                categorias.add(cat);
            }
        }
        
        for(Trabajadorbbdd trab:trabajadorADevolver){
            for(Empresas empresa:empresas){
                if(trab.getEmpresas().getCif() == empresa.getCif()){
                    trab.setEmpresas(empresa);
                }
            }
            
            for(Categorias categoria:categorias){
                if(trab.getCategorias().getNombreCategoria() == categoria.getNombreCategoria()){
                    trab.setCategorias(categoria);
                }
            }

        }
        
        return trabajadorADevolver;
        
        
    }
}
