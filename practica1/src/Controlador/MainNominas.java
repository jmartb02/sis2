/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import java.io.IOException;
import java.util.*;
import vista.*;
import java.util.Scanner;
import java.io.File;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MainNominas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       ejercicio2();
       errorXML();
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
             ventana.imprimirPorPantalla(Consulta.aumentarSalarioBase(DNI));
             ventana.imprimirPorPantalla(consulta.destruir(DNI));
             
         
         }else{
             ventana.imprimirPorPantalla("No hemos encontrado el trabajador en nuestro sistema.");
         }
         
         consulta.desconectar();
    }
    
    public static void ejercicio2() throws IOException{
        ArrayList<Trabajadorbbdd> listaTrabajadores;
        ConsultaExcel consulta = new ConsultaExcel("src/resources/SistemasInformacionII.xlsx");
        listaTrabajadores = consulta.leer();
        int aux = 0;
        int contador=0;
        int[] dnis = new int[listaTrabajadores.size()];
        for(Trabajadorbbdd trabajador: listaTrabajadores){
            System.out.println(trabajador.getNifnie());
            if(trabajador.getNifnie()==""){
                contador++;
             }
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
        } System.out.println(contador);
        
       if(!comprobar(listaTrabajadores, dnis)){
           //sustituir los DNI de la lista de trabajadores que hay almacenado en listaTrabajadores.
       }
    }
    public static void errorXML() throws IOException{
            ArrayList<Trabajadorbbdd> listaTrabajadores;
            ConsultaExcel consulta = new ConsultaExcel("src/resources/SistemasInformacionII.xlsx");
            listaTrabajadores = consulta.leer();
          try {
              Integer aux= 1;
            StreamResult result = new StreamResult(new File("src/resources/error.xml"));
           //parametros de creacion del documento con elemento root trabajadores
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            DOMSource source = new DOMSource(doc);
            Element rootElement =  doc.createElement("Trabajadores");
            doc.appendChild(rootElement);
            
             ArrayList<String> newList = new ArrayList<String>();
            
            for(Trabajadorbbdd trabajador: listaTrabajadores){
                
               
                
                
                
                
                aux++;
                    //etiquetas documento XML
            Element nombre = doc.createElement("Nombre");
            Element primerApellido = doc.createElement("PrimerApellido");
            Element segundoApellido = doc.createElement("PrimerApellido");
            Element categoria = doc.createElement("categoria");
            Element empresa = doc.createElement("empresa");

                 //comprobamos que sea blanco o que el DNI este repetido para insertarlo
                 //trabajador.getNifnie()==""
          if(trabajador.getNifnie()==""||newList.contains(trabajador.getNifnie())){
              
                Element staff = doc.createElement("Trabajador");
                rootElement.appendChild(staff);
		staff.setAttribute("id", aux.toString());

		// elemento nombre
		
		nombre.appendChild(doc.createTextNode(trabajador.getNombre()));
		staff.appendChild(nombre);

		//elemento Primer Apellido
		
		primerApellido.appendChild(doc.createTextNode(trabajador.getApellido1()));
		staff.appendChild(primerApellido);
                
                // elemento Segundo apellido
		
		segundoApellido.appendChild(doc.createTextNode(trabajador.getApellido2()));
		staff.appendChild(segundoApellido);

		// elemento categoria
		
		categoria.appendChild(doc.createTextNode(trabajador.getCategorias().getNombreCategoria()));
		staff.appendChild(categoria);

		// elemento empresa
		
		empresa.appendChild(doc.createTextNode(trabajador.getEmpresas().getNombre()));
		staff.appendChild(empresa);
                }
          
                if (!newList.contains(trabajador.getNifnie())) { 

                      newList.add(trabajador.getNifnie()); 
                  }

		// escribimos el contenido en el XML
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();  
		transformer.transform(source, result);
            }//fin de for
              
            
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
    
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
    
}
