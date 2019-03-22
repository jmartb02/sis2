package modelo;

import Controlador.NewHibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class Consulta {
    private static SessionFactory sf;
    private static Session session;
    public Consulta(){
        
    }
    
    public static void conectar(){
        sf = NewHibernateUtil.getSessionFactory();
        session = sf.openSession();
    }
    
    public static void desconectar(){
        
        session.close();
        sf.close();
    }
    
    public static boolean existe(String DNI){
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
       
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();

        if(listaCategorias.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    public static String destruir(String DNI){
        String frase = "";
        Transaction transaction = session.beginTransaction();
        
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        Trabajadorbbdd trabajador = listaCategorias.get(0);
        
        query = "Select c from Nomina c where IdTrabajador = '"+trabajador.getIdTrabajador()+"'";
        
        List<Nomina> listaNominas = session.createQuery(query).list();
        
        query = "Select c from Nomina c where IRPF >= "+listaNominas.get(0).getIrpf();
        
        List<Nomina> listaPosiblesNominas = session.createQuery(query).list();
        
        if(listaPosiblesNominas.isEmpty()){
            frase = "No hay nadie que iguale o supere los "+listaNominas.get(0).getIrpf()+" de IRPF";
        }else{
            query = "DELETE from Nomina where IRPF >= "+listaNominas.get(0).getIrpf();
            Query queryEx = session.createQuery(query);
            int count = queryEx.executeUpdate();
            frase ="Borrados los "+count+" que eran mayores a "+listaNominas.get(0).getIrpf();
            transaction.commit();
        }
        
        return frase;
    }
    
    public static String mostrar(String DNI){
         String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        Trabajadorbbdd trabajador = listaCategorias.get(0);
        query = "Select c from Trabajadorbbdd c where IdEmpresa = '"+trabajador.getEmpresas().getIdEmpresa()+"'";
        List<Trabajadorbbdd> listaTrabajadores = session.createQuery(query).list();
        
       return (trabajador.getNombre()+ " "+trabajador.getApellido1()+" "+trabajador.getApellido2()+
        " con DNI "+trabajador.getNifnie() + " y su empresa "+trabajador.getEmpresas().getNombre() +" tiene "+listaTrabajadores.size()+ " trabajadores");
   
    }
    
    public static String aumentarSalarioBase(String DNI){
        
        Transaction transaction = session.beginTransaction();
        
        String query = "Select c from Trabajadorbbdd c where nifnie = '"+DNI+"'";
        List<Trabajadorbbdd> listaCategorias = session.createQuery(query).list();
        Trabajadorbbdd trabajador = listaCategorias.get(0);
        query = "Select c from Categorias c where IdCategoria = '"+trabajador.getCategorias().getIdCategoria()+"'";
        List<Categorias> categorias = session.createQuery(query).list();
        Categorias categoria = categorias.get(0);
        double salarioBaseAnterior = categoria.getSalarioBaseCategoria();
        double salarioActual = salarioBaseAnterior - 500;
        String frase = "Al trabajador "+trabajador.getNombre()+" con DNI "+DNI+" se ha reducido su salario de "+salarioBaseAnterior+" a "+salarioActual;
        query = "UPDATE from Categorias set SalarioBaseCategoria = "+salarioActual+" where IdCategoria = "+trabajador.getCategorias().getIdCategoria();
      
        Query queryEx = session.createQuery(query);
        int count = queryEx.executeUpdate();
        
        
       transaction.commit();
       
       return frase;
    }
}
