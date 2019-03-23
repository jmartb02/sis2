/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

/**
 *
 * @author Sheik
 */
public class CrearXML {
    public CrearXML(){
    }
    
    
    public static void errorXML(ArrayList<Trabajadorbbdd> listaTrabajadores) throws IOException, ParserConfigurationException{
            ConsultaExcel consulta = new ConsultaExcel("src/resources/SistemasInformacionII.xlsx");

          try {
              Integer aux= 1;
            StreamResult result = new StreamResult(new File("src/resources/Errores.xml"));
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
                }//fin de if
                // si no esta en la lista añadimos el dni
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
}
