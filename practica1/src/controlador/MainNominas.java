/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import vista.*;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        ejercicio3();

    }
    
    public static void ejercicio1(){
        //DNI de prueba, Carolina 09741138V, IdCategoria 230, salario base por categoria 20000
        Ventana ventana = new Ventana();
         Consulta consulta = new Consulta();
         consulta.conectar();
         Scanner scanner = new Scanner(System.in);
         ventana.imprimirPorPantalla("Ingrese un DNI");
         String DNI = scanner.nextLine();
         if(consulta.existe(DNI)){
             ventana.imprimirPorPantalla(consulta.mostrar(DNI));
             ventana.imprimirPorPantalla(consulta.aumentarSalarioBase(DNI));
             ventana.imprimirPorPantalla(consulta.destruir(DNI));
             
         
         }else{
             ventana.imprimirPorPantalla("No hemos encontrado el trabajador en nuestro sistema.");
         }
         
         consulta.desconectar();
    }
    
    public static void ejercicio2() throws IOException, ParserConfigurationException{
        ArrayList<Trabajadorbbdd> listaTrabajadores;
        ConsultaExcel consulta = new ConsultaExcel("src\\resources\\SistemasInformacionII.xlsx");
        listaTrabajadores = consulta.leer();
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
        
       if(!comprobar(listaTrabajadores, dnis)){
           //sustituir los DNI de la lista de trabajadores que hay almacenado en listaTrabajadores.
           consulta.escribir(listaTrabajadores);
       }
       
       CrearXML XML = new CrearXML();
       XML.errorXML(listaTrabajadores);
    }
    
    public static boolean comprobar(ArrayList<Trabajadorbbdd> listaTrabajadores, int[] dnis) {
        boolean correcto = true;
        String abecedario = "TRWAGMYFPDXBNJZSQVHLCKE";
        char[] letras = abecedario.toCharArray();
        int posicion = 0;
        for(Trabajadorbbdd trabajador: listaTrabajadores){
            if(dnis[posicion] < 24){
                if(!String.valueOf(letras[dnis[posicion]]).equals(trabajador.getNifnie().substring(trabajador.getNifnie().length()-1))){
                    correcto = false;
                    trabajador.setNifnie(trabajador.getNifnie().subSequence(0, trabajador.getNifnie().length()-1).toString() + letras[dnis[posicion]]);
                }
            }
            
            posicion++;
        }
       return correcto;
    }
    
    public static void ejercicio3() throws IOException, ParserConfigurationException{
        ArrayList<Trabajadorbbdd> listaTrabajadores;
        ArrayList<Trabajadorbbdd> listaTrabajadoresError = new ArrayList<Trabajadorbbdd>();
        List<Integer> id = new ArrayList<Integer>();
        ConsultaExcel consulta = new ConsultaExcel("src\\resources\\SistemasInformacionII.xlsx");
        listaTrabajadores = consulta.leer();
        
        int aux = 0;
        for(Trabajadorbbdd trab: listaTrabajadores){
            String[] IBAN = new String[1];
            IBAN[0] = "";
             boolean correctoDigito = digitoControl(trab);
            boolean correctoIBAN = calcularIBAN(trab, IBAN);
           trab.setIban(IBAN[0]);
           
           if(!correctoDigito || !correctoIBAN){
               listaTrabajadoresError.add(trab);
               id.add(aux);
           }
           aux++;
        }
        int[] IDs = new int[id.size()];
        for(int i = 0; i < id.size(); i++){
            IDs[i] = id.get(i);
        }
        consulta.escribir(listaTrabajadores);
        
        if(!listaTrabajadoresError.isEmpty()){
            CrearXML XML = new CrearXML();
            XML.erroresCCC(listaTrabajadoresError, IDs);  
        }
     
    }
    
    public static boolean digitoControl(Trabajadorbbdd trabajador){
        String pais = trabajador.getPaisOrigen();
        String entidadBancaria = trabajador.getCodigoCuenta().substring(0, 4);
        String entidadOficina = trabajador.getCodigoCuenta().substring(4, 8);
        String digitosControl = trabajador.getCodigoCuenta().substring(8, 10);
        String cuenta = trabajador.getCodigoCuenta().substring(10, 20);
        String entidad = "00"+entidadBancaria+entidadOficina;
        
        int digitoControlEntidad = calculoDigito(entidad);
        int segundoDigito = calculoDigito(cuenta);
        String comprobarPrimero = ""+digitoControlEntidad;
        String compararPrimero = digitosControl.substring(0, 1);
        String comprobarSegundo = ""+segundoDigito;
        String compararSegundo = digitosControl.substring(1,2);
        if(Integer.parseInt(compararPrimero) == Integer.parseInt(comprobarPrimero) && Integer.parseInt(compararSegundo) == Integer.parseInt(comprobarSegundo)){
            return true;
        }else{
            String codigoCuenta = entidadBancaria+entidadOficina+comprobarPrimero+comprobarSegundo+cuenta;
            System.out.println(codigoCuenta);
            trabajador.setCodigoCuenta(codigoCuenta);
            return false;
        }
    }
    
    public static int calculoDigito(String entidades){
        int digitoControlEntidad = 0;
        for(int i = 0; i < entidades.length(); i++){
            digitoControlEntidad += ((int) Math.pow(2.0, i) * Double.parseDouble(entidades.substring(i, i+1)));
        }
        
        digitoControlEntidad = digitoControlEntidad % 11;
        digitoControlEntidad -=  11;
        if(digitoControlEntidad < 0){
            digitoControlEntidad *= -1;
        }
        if(digitoControlEntidad == 10){
            digitoControlEntidad = 1;
        }
        if(digitoControlEntidad == 11){
            digitoControlEntidad =0;
        }
        return digitoControlEntidad;
    }
    
    public static boolean calcularIBAN(Trabajadorbbdd trabajador, String[] IBAN){
        String pais = trabajador.getPaisOrigen();
        String entidadBancaria = trabajador.getCodigoCuenta().substring(0, 4);
        String entidadOficina = trabajador.getCodigoCuenta().substring(4, 8);
        String digitosControl = trabajador.getCodigoCuenta().substring(8, 10);
        String cuenta = trabajador.getCodigoCuenta().substring(10, 20);
        
        String digitos = entidadBancaria+entidadOficina+digitosControl+cuenta;
        char[] letras = pais.toCharArray();
        String[] valorLetras = new String[2];
        
        for(int i = 0; i < letras.length; i++){
            int numero = (int) letras[i];
            numero -= 55;
            valorLetras[i] = numero+ "" ;
            digitos += numero;
        }
        digitos+= "00";
        
        BigInteger numeroCCC = new BigInteger(digitos);
        BigInteger numeroModulo = new BigInteger("97");
        numeroCCC = numeroCCC.mod(numeroModulo);
        int numero = numeroCCC.intValue();
        numero = 98 - numero;
        String digitoIban;
        if(numero > 9){
            digitoIban = ""+numero;
        }else{
            digitoIban = "0"+numero;
        }
        
        String digitosAComprobar = entidadBancaria+entidadOficina+digitosControl+cuenta+valorLetras[0]+valorLetras[1]+digitoIban;
        BigInteger numeroAComprobar = new BigInteger(digitosAComprobar);
        BigInteger nuevoModulo = new BigInteger("97");
        numeroAComprobar = numeroAComprobar.mod(nuevoModulo);
        int resultado = numeroAComprobar.intValue();
        
        IBAN[0] = pais+digitoIban+entidadBancaria+entidadOficina+digitosControl+cuenta;
        if(resultado == 1){
            return true;
        }else{
            return false;
        }
    }
}
