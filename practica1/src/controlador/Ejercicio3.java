/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import Modelo.CrearXML;
import Modelo.Trabajadorbbdd;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author Sheik
 */
public class Ejercicio3 {
    public Ejercicio3(){}
    
    public static void run(ArrayList<Trabajadorbbdd> listaTrabajadores) throws IOException, ParserConfigurationException{      
        calculoIBAN(listaTrabajadores);
        calculoEmail(listaTrabajadores);
    }
    
    public static void calculoEmail(ArrayList<Trabajadorbbdd> listaTrabajadores) throws IOException{
  
    ArrayList<String> repetido = new ArrayList<>();
    
    String letraPA,letraSA,nombre,empresa, email;
    int aux= 0;

        for(Trabajadorbbdd trabajador: listaTrabajadores){
            if(trabajador.getEmail()==null){
                letraPA = trabajador.getApellido1().substring(0, 1).toLowerCase();
                nombre = trabajador.getNombre().substring(0,1).toLowerCase();
                empresa=trabajador.getEmpresas().getNombre().toLowerCase();
            
                if(trabajador.getApellido2()==""){
                    String aux2 = String.format("%02d", aux);
                    email = letraPA+ nombre+aux2+"@"+empresa+".es";
                        if(repetido.contains(email)){
                            aux ++;
                            String aux3 = String.format("%02d", aux);
                            email = letraPA+ nombre+aux3+"@"+empresa+".es";
                            aux=0;
                       }
                        repetido.add(email);
                        trabajador.setEmail(email);
                }
            if(trabajador.getApellido2()!=""){
                letraSA = trabajador.getApellido2().substring(0,1).toLowerCase();
                String aux2 = String.format("%02d", aux);
                email = letraPA+ letraSA+ nombre+aux2+"@"+empresa+".es";
                    if(repetido.contains(email)){
                        aux ++;
                        String aux3 = String.format("%02d", aux);
                        email = letraPA+ letraSA+ nombre+aux3+"@"+empresa+".es";
                        aux=0;
                    }
 
            repetido.add(email);
            trabajador.setEmail(email);
            
            }
            
        }
        }
    }
    public static void calculoIBAN(ArrayList<Trabajadorbbdd> listaTrabajadores) throws IOException, ParserConfigurationException{
        
        ArrayList<Trabajadorbbdd> listaTrabajadoresError = new ArrayList<Trabajadorbbdd>();
        List<Integer> id = new ArrayList<Integer>();
        
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
        
        if(!listaTrabajadoresError.isEmpty()){
            CrearXML XML = new CrearXML();
            XML.erroresCCC(listaTrabajadoresError, IDs);  
        }
     
    }
    
    public static boolean digitoControl(Trabajadorbbdd trabajador){
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
