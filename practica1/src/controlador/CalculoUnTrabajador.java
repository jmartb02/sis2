/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import Modelo.Parametro;
import Modelo.Trabajadorbbdd;
import Modelo.Nomina;
import java.util.Date;
import vista.*;

/**
 *
 * @author Sheik
 */
public class CalculoUnTrabajador {    

    private double salarioBase;
     
    private double devengos;
    private double deducciones;

    private double costeTotalParaEmpresario;
    private double costeTotalTrabajador;
    
    private Nomina nomina;
    private Trabajadorbbdd trabajador;
    private Parametro parametro;
    private CalculoTrabajador calculo;
    private Date fecha;
    private boolean esExtra;
    private String mes;
    
    public CalculoUnTrabajador(Trabajadorbbdd trabajador,Parametro parametro, Date fecha, boolean esExtra, String mes){
        this.mes = mes;
        this.esExtra = esExtra;
        this.trabajador = trabajador;
        this.parametro = parametro;
        this.calculo = new CalculoTrabajador(this.parametro, trabajador, fecha, esExtra);
        this.fecha = fecha;
        this.nomina = new Nomina();
    }
    
    public void asignar(){
        this.salarioBase = ((double)Math.round(this.calculo.calculoBase() * 100d)/100d);
        this.nomina.setValorProrrateo(this.calculo.calculoProrateo());
        this.nomina.setImporteComplementoMes(this.calculo.calculoComplemento());
        this.nomina.setSeguridadSocialTrabajador(this.calculo.calculoContingecias());
        this.nomina.setDesempleoTrabajador(this.calculo.calculoDesempleo());
        this.nomina.setFormacionTrabajador(this.calculo.calculoFormacion());
        this.devengos = this.salarioBase+this.nomina.getValorProrrateo()+this.nomina.getImporteComplementoMes()+this.calculo.calculoAntiguedad();
        this.devengos = ((double)Math.round(this.devengos * 100d)/100d);
        this.deducciones = this.nomina.getSeguridadSocialTrabajador()+this.nomina.getDesempleoTrabajador()+this.nomina.getFormacionTrabajador()+this.calculo.calculoIRFP();
        this.deducciones = ((double)Math.round(this.deducciones * 100d)/100d);
        this.nomina.setLiquidoNomina((this.devengos-this.deducciones));
        this.nomina.setBaseEmpresario(this.calculo.getCalculoEmpresarioBase());
        this.costeTotalParaEmpresario = this.calculo.calculoContigenciasComunes()+this.calculo.calculoDesempleoEmpresario()+
                this.calculo.calculoFormacionEmpresario()+this.calculo.calculoAccidentesEmpresario()+
                this.calculo.calculoFogasa();
        this.costeTotalParaEmpresario = this.calculo.redondear(this.costeTotalParaEmpresario);
        this.costeTotalTrabajador = this.calculo.redondear(this.costeTotalParaEmpresario+this.devengos);
        this.nomina.setBrutoAnual( this.calculo.getBrutoAnual());
    }
    public void imprimirresultadoDatos(){
    String empresa ="La empresa "+this.trabajador.getEmpresas().getNombre()+ " con CIF: "+this.trabajador.getEmpresas().getCif();
    String trabajador = ""+this.trabajador.getNombre() + " "+this.trabajador.getApellido1()+" "+this.trabajador.getApellido2()
            + " con DNI: "+this.trabajador.getNifnie()+" perteneciente a la categoria "+ this.trabajador.getCategorias().getNombreCategoria()
            +" con un bruto anual de "+this.nomina.getBrutoAnual()+ " tiene el siguiente IBAN: "+this.trabajador.getIban()+
            " y se dio de alta el "+this.trabajador.getFechaAlta();
    
    String fechaNomina = "Nomina: "+this.fecha.toString();
    String importes = "Salario base: "+this.salarioBase+ " prorrateo Mes: "+this.nomina.getValorProrrateo()
            + " complemento mes: "+this.nomina.getImporteComplementoMes()+ " antiguedad: "+this.calculo.calculoAntiguedad();

    String descuentosTrabajador = "Contingencias Generales: "+this.calculo.getContingencias()+"% de "
            +this.calculo.getCalculoEmpresarioBase()+" es: "
            +this.nomina.getSeguridadSocialTrabajador()
            + "\nDesempleo: "+this.calculo.getDesempleo()+"% de "+this.calculo.getCalculoEmpresarioBase()+
            " es: "+this.nomina.getDesempleoTrabajador()+
            "\nCuota de formacion: "+this.calculo.getFormacion()+"% de "+this.calculo.getCalculoEmpresarioBase()+
            " es: "+this.nomina.getFormacionTrabajador()+
            "\nIRPF: "+this.calculo.getIRPF()+ "% de "+this.calculo.getCalculoBaseIRPF()+" es "+this.calculo.calculoIRFP();
    String devengosYDeducciones = "Devengos: "+devengos+ " deducciones: "+deducciones+" liquido a percibir: "+((double)Math.round(this.nomina.getLiquidoNomina() * 100d)/100d);

    
    String pagosEmpresario = "Base sobre lo que se produce: "+this.nomina.getBaseEmpresario()+ 
          "\nConingencias comunes "+this.calculo.getContingenciasEmpresario()+"% "+this.calculo.calculoContigenciasComunes()+
            "\nDesempleo: "+this.calculo.getDesempleoEmpresario()+ "% "+this.calculo.calculoDesempleoEmpresario()+
            "\nFormacion: "+this.calculo.getFormacionEmpresaio()+"% "+this.calculo.calculoFormacionEmpresario()+
            "\nAccidentes de trabajo "+this.calculo.getAccidentesEmpresario()+"% "+this.calculo.calculoAccidentesEmpresario()+
            "\nFOGASA "+this.calculo.getFogasa()+"% "+this.calculo.calculoFogasa();
    String total = "Coste total para el empresario: "+costeTotalParaEmpresario+"\nCoste total trabajador "+this.costeTotalTrabajador;
   if(this.esExtra){
       empresa = "EXTRA "+this.mes+"\n"+empresa;
   }
    String datos = empresa+ "\n"+trabajador+"\n"+fechaNomina+"\n"+importes+"\n"+descuentosTrabajador+"\n"+devengosYDeducciones+
            "\n"+pagosEmpresario+"\n"+total;
    
    Ventana ventana = new Ventana();
            ventana.imprimirPorPantalla(datos);
    }
   
    
    public void run(){
        asignar();
        imprimirresultadoDatos();
    }
    
}
