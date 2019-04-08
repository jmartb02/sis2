/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import vista.*;

/**
 *
 * @author Sheik
 */
public class Ejercicio4 {
    private String empresaNombre;
    private String empresaCIF;
    
    private String trabajadorCategoria;
    private String trabajadorBrutoAnual;
    private String trabajadorFechaDeAlta;
    private String trabajadorIBAN;
    private String trabajadorNombre;
    private String trabajadorApellidos;
    private String trabajadorNIF;
    
    private String fechaNomina;
    
    private String salarioBase;
    private String prorrateoMes;
    private String complementoMes;
    private String antiguedadMes;
    
    private String seguridadSocial;
    private String desempleo;
    private String cuotaDeFormacion;
    private String IRPF;
    private String porcentajesAAplicar;
    private String valorSobreImporteCalculado;
    
    private String devengos;
    private String deducciones;
    private String liquidoAPercibir;
    
    private String baseSobreProduce;
    private String porcentajePagos;
    private String importesDeTrabajo;
    private String FOGASA;
    private String sumaTotalPagos;
    
    private String costeTotalParaEmpresario;
    
    
    public Ejercicio4(){
        
    }
    public void imprimirresultadoDatos(){
    String empresa ="La empresa "+empresaNombre+ " con CIF: "+empresaCIF;
    String trabajador = trabajadorNombre+ " "+trabajadorApellidos+ " con DNI: "+trabajadorNIF+" perteneciente a la categoria "+ trabajadorCategoria
            +" con un bruto anual de "+trabajadorBrutoAnual+ " tiene el siguiente IBAN: "+trabajadorIBAN+
            " y se dio de alta el "+trabajadorFechaDeAlta;
    
    String fechaNomina = this.fechaNomina;
    String importes = "Salario base: "+salarioBase+ " prorrateo Mes: "+prorrateoMes
            + " complemento mes: "+complementoMes+ " antiguedad: "+antiguedadMes;

    String descuentosTrabajador = "Seguridad Social: "+seguridadSocial+ " desempleo: "+desempleo+" cuota de formacion: "+
            cuotaDeFormacion+ " porcentajes a aplicar: "+porcentajesAAplicar+ " valor sobre importe calculado: "+
            valorSobreImporteCalculado+ " IRPF: "+IRPF;
    String devengosYDeducciones = "Devengos: "+devengos+ " deducciones: "+deducciones+" liquido a percibir: "+liquidoAPercibir;

    
    String pagosEmpresario = "Base sobre lo que se produce: "+baseSobreProduce+ " porcenajte de pagos: "+porcentajePagos+
            " importe de trabajo: "+importesDeTrabajo+ " FOGASA: "+FOGASA+ " Suma total de pagos"+sumaTotalPagos;
    
    String total = "Coste total para el empresario: "+costeTotalParaEmpresario;
   
    String datos = empresa+ "\n"+trabajador+"\n"+fechaNomina+"\n"+importes+"\n"+descuentosTrabajador+"\n"+devengosYDeducciones+
            "\n"+pagosEmpresario+"\n"+total;
    
    Ventana ventana = new Ventana();
            ventana.imprimirPorPantalla(datos);
    }
   
    
}
