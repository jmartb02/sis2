/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si2hibernate;

import Modelo.*;
import Controlador.NewHibernateUtil;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Sheik
 */
public class SI2Hibernate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
         Scanner scanner = new Scanner(System.in);
         System.out.println("Ingrese un DNI");
         String DNI = scanner.nextLine();
         if(existe(DNI)){
            mostrar(DNI);
            aumentarSalarioBase(DNI);
            destruir("09741138V");
            
         
         }else{
             System.out.println("No hemos encontrado el trabajador en nuestro sistema.");
         }
         
    }
    
    public static void mostrar(String DNI){
        
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        Trabajadorbbdd trabajador = listaCategorias.get(0);
        query = "Select c from Trabajadorbbdd c where IdEmpresa = '"+trabajador.getEmpresas().getIdEmpresa()+"'";
        List<Trabajadorbbdd> listaTrabajadores = session.createQuery(query).list();
            
             
        System.out.println(trabajador.getNombre()+ " "+trabajador.getApellido1()+" "+trabajador.getApellido2()+
        " con DNI "+trabajador.getNifnie() + " y su empresa "+trabajador.getEmpresas().getNombre() +" tiene "+listaTrabajadores.size()+ " trabajadores");
   
        
        session.save(sf);
        session.update(sf);
        session.delete(sf);
        session.clear();
        session.close();
        sf.close();
    }
    
    public static void aumentarSalarioBase(String DNI){
        
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        Trabajadorbbdd trabajador = listaCategorias.get(0);
        query = "Select c from Categorias c where IdCategoria = '"+trabajador.getCategorias().getIdCategoria()+"'";
        List<Categorias> categorias = session.createQuery(query).list();
        Categorias categoria = categorias.get(0);
        double salarioBaseAnterior = categoria.getSalarioBaseCategoria();
        double salarioActual = salarioBaseAnterior - 500;
        
        query = "UPDATE from Categorias set SalarioBaseCategoria = "+salarioActual+" where IdCategoria = "+trabajador.getCategorias().getIdCategoria();
      
        Query queryEx = session.createQuery(query);
        int count = queryEx.executeUpdate();
        
        
       transaction.commit();
       session.clear();
       session.close();
        sf.close();
    }
   
    public static boolean existe(String DNI){
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        
        
        session.save(sf);
        session.update(sf);
        session.delete(sf);
        session.clear();
        session.close();
        sf.close();
        
        if(listaCategorias.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    public static void destruir(String DNI){
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        Trabajadorbbdd trabajador = listaCategorias.get(0);
        
        query = "Select c from Nomina c where IdTrabajador = '"+trabajador.getIdTrabajador()+"'";
        
        List<Nomina> listaNominas = session.createQuery(query).list();
        
        
        query = "DELETE from Nomina where IRPF >= "+listaNominas.get(0).getIrpf();
        Query queryEx = session.createQuery(query);
        int count = queryEx.executeUpdate();
        System.out.println("Borrados los "+count+" que eran mayores a "+listaNominas.get(0).getIrpf());
        
       transaction.commit();
        
        session.save(sf);
        session.update(sf);
        session.delete(sf);
        session.clear();
        session.close();
        sf.close();
    }
    
}
