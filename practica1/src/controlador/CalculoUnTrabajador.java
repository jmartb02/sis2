/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import modelo.Parametro;
import modelo.Trabajadorbbdd;
import modelo.Nomina;
import java.util.Date;
import vista.*;

/**
 *
 * @author Sheik
 */
public class CalculoUnTrabajador {    

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getDevengos() {
        return devengos;
    }

    public void setDevengos(double devengos) {
        this.devengos = devengos;
    }

    public double getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(double deducciones) {
        this.deducciones = deducciones;
    }

    public double getCosteTotalParaEmpresario() {
        return costeTotalParaEmpresario;
    }

    public void setCosteTotalParaEmpresario(double costeTotalParaEmpresario) {
        this.costeTotalParaEmpresario = costeTotalParaEmpresario;
    }

    public double getCosteTotalTrabajador() {
        return costeTotalTrabajador;
    }

    public void setCosteTotalTrabajador(double costeTotalTrabajador) {
        this.costeTotalTrabajador = costeTotalTrabajador;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    public Trabajadorbbdd getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajadorbbdd trabajador) {
        this.trabajador = trabajador;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    public CalculoTrabajador getCalculo() {
        return calculo;
    }

    public void setCalculo(CalculoTrabajador calculo) {
        this.calculo = calculo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEsExtra() {
        return esExtra;
    }

    public void setEsExtra(boolean esExtra) {
        this.esExtra = esExtra;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    private double salarioBase;
     
    private double devengos;
    private double deducciones;

    private double costeTotalParaEmpresario;
    private double costeTotalTrabajador;
    
    private Trabajadorbbdd trabajador;
    private Parametro parametro;
    private CalculoTrabajador calculo;
    private Date fecha;
    private boolean esExtra;
    private String mes;
    private Nomina nomina;
    private double calculoEmpresarioBase;
    
    public CalculoUnTrabajador(Trabajadorbbdd trabajador,Parametro parametro, Date fecha, boolean esExtra, String mes){
        this.mes = mes;
        this.esExtra = esExtra;
        this.trabajador = trabajador;
        this.parametro = parametro;
        this.nomina = new Nomina();
        this.calculo = new CalculoTrabajador(this.getParametro(), trabajador, fecha, esExtra, this.nomina);
        this.fecha = fecha;
    }
    
    public void asignar(){
        this.setSalarioBase((double) Math.round(this.getCalculo().calculoBase() * 100d) / 100d);
        this.getNomina().setValorProrrateo(this.getCalculo().calculoProrateo());
        this.getNomina().setImporteComplementoMes(this.getCalculo().calculoComplemento());
        this.getNomina().setSeguridadSocialTrabajador(this.getCalculo().calculoContingecias());
        this.getNomina().setDesempleoTrabajador(this.getCalculo().calculoDesempleo());
        this.getNomina().setFormacionTrabajador(this.getCalculo().calculoFormacion());
        this.setDevengos(this.getSalarioBase() + this.getNomina().getValorProrrateo() + this.getNomina().getImporteComplementoMes() + this.getCalculo().calculoAntiguedad());
        this.setDevengos((double) Math.round(this.getDevengos() * 100d) / 100d);
        this.setDeducciones(this.getNomina().getSeguridadSocialTrabajador() + this.getNomina().getDesempleoTrabajador() + this.getNomina().getFormacionTrabajador() + this.getCalculo().calculoIRFP());
        this.setDeducciones((double) Math.round(this.getDeducciones() * 100d) / 100d);
        this.getNomina().setLiquidoNomina((this.getDevengos()-this.getDeducciones()));
        this.getNomina().setBaseEmpresario(this.getCalculo().getCalculoEmpresarioBase());
        this.setCosteTotalParaEmpresario(this.getCalculo().calculoContigenciasComunes() + this.getCalculo().calculoDesempleoEmpresario() + this.getCalculo().calculoFormacionEmpresario() + this.getCalculo().calculoAccidentesEmpresario() + this.getCalculo().calculoFogasa());
        this.setCosteTotalParaEmpresario((double) this.getCalculo().redondear(this.getCosteTotalParaEmpresario()));
        this.setCosteTotalTrabajador((double) this.getCalculo().redondear(this.getCosteTotalParaEmpresario() + this.getDevengos()));
        this.getNomina().setBrutoAnual(this.getCalculo().getBrutoAnual());
        
        this.nomina.setTrabajadorbbdd(this.trabajador);
        LocalDate trabajadorDate = this.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year=trabajadorDate.getYear();
        int month = trabajadorDate.getMonthValue();
        this.nomina.setMes(month);
        this.nomina.setAnio(year);
        this.nomina.setImporteTrienios(this.getCalculo().calculoAntiguedad());
        this.nomina.setImporteSalarioMes(this.getSalarioBase());
        this.nomina.setImporteComplementoMes(this.getNomina().getImporteComplementoMes());
        this.nomina.setValorProrrateo(this.getNomina().getValorProrrateo());
        this.nomina.setBrutoAnual(this.getNomina().getBrutoAnual());
        this.nomina.setIrpf(this.getCalculo().getIRPF());
        this.nomina.setImporteIrpf(this.getCalculo().calculoIRFP());
        this.nomina.setBaseEmpresario(this.getNomina().getBaseEmpresario());
        this.nomina.setSeguridadSocialEmpresario(this.getCalculo().getCalculoEmpresarioBase());
        this.nomina.setImporteSeguridadSocialEmpresario(this.getCalculo().calculoContigenciasComunes());
        this.nomina.setDesempleoEmpresario(this.getCalculo().getDesempleoEmpresario());
        this.nomina.setImporteDesempleoEmpresario(this.getCalculo().calculoDesempleoEmpresario());
        this.nomina.setFormacionEmpresario(this.getCalculo().getFormacionEmpresaio());
        this.nomina.setImporteFormacionEmpresario(this.getCalculo().calculoFormacionEmpresario());
        this.nomina.setAccidentesTrabajoEmpresario(this.getCalculo().getAccidentesEmpresario());
        this.nomina.setImporteAccidentesTrabajoEmpresario(this.getCalculo().calculoAccidentesEmpresario());
        this.nomina.setFogasaempresario(this.getCalculo().getFogasa());
        this.nomina.setImporteFogasaempresario(this.getCalculo().calculoFogasa());
        
        this.nomina.setSeguridadSocialTrabajador(this.getCalculo().getContingencias());
        this.nomina.setImporteSeguridadSocialTrabajador(this.getNomina().getSeguridadSocialTrabajador());
        this.nomina.setDesempleoTrabajador(this.getCalculo().getDesempleo());
        this.nomina.setImporteDesempleoTrabajador(this.getNomina().getDesempleoTrabajador());
        this.nomina.setFormacionTrabajador(this.getCalculo().getFormacion());
        this.nomina.setImporteFormacionTrabajador(this.getNomina().getFormacionTrabajador());
        this.nomina.setBrutoNomina(getDevengos());
        this.nomina.setLiquidoNomina(((double)Math.round(this.getNomina().getLiquidoNomina() * 100d)/100d));
        this.nomina.setCosteTotalEmpresario(getCosteTotalParaEmpresario());
        
    }
    public void imprimirresultadoDatos() throws IOException{
    String empresa ="La empresa "+this.getTrabajador().getEmpresas().getNombre()+ " con CIF: "+this.getTrabajador().getEmpresas().getCif();
    String trabajador = ""+this.getTrabajador().getNombre() + " "+this.getTrabajador().getApellido1()+" "+this.getTrabajador().getApellido2()
            + " con DNI: "+this.getTrabajador().getNifnie()+" perteneciente a la categoria "+ this.getTrabajador().getCategorias().getNombreCategoria()
            +" con un bruto anual de "+this.getNomina().getBrutoAnual()+ " tiene el siguiente IBAN: "+this.getTrabajador().getIban()+
            " y se dio de alta el "+this.getTrabajador().getFechaAlta();
    
    String fechaNomina = "Nomina: "+this.getFecha().toString();
    String importes = "Salario base: "+this.getSalarioBase()+ " prorrateo Mes: "+this.getNomina().getValorProrrateo()
            + " complemento mes: "+this.getNomina().getImporteComplementoMes()+ " antiguedad: "+this.getCalculo().calculoAntiguedad();

    String descuentosTrabajador = "Contingencias Generales: "+this.getCalculo().getContingencias()+"% de "
            +this.getCalculo().getCalculoEmpresarioBase()+" es: "
            +this.getNomina().getSeguridadSocialTrabajador()
            + "\nDesempleo: "+this.getCalculo().getDesempleo()+"% de "+this.getCalculo().getCalculoEmpresarioBase()+
            " es: "+this.getNomina().getDesempleoTrabajador()+
            "\nCuota de formacion: "+this.getCalculo().getFormacion()+"% de "+this.getCalculo().getCalculoEmpresarioBase()+
            " es: "+this.getNomina().getFormacionTrabajador()+
            "\nIRPF: "+this.getCalculo().getIRPF()+ "% de "+this.getCalculo().getCalculoBaseIRPF()+" es "+this.getCalculo().calculoIRFP();
    String devengosYDeducciones = "Devengos: "+getDevengos()+ " deducciones: "+getDeducciones()+" liquido a percibir: "+((double)Math.round(this.getNomina().getLiquidoNomina() * 100d)/100d);

    
    String pagosEmpresario = "Base sobre lo que se produce: "+this.getNomina().getBaseEmpresario()+ 
          "\nConingencias comunes "+this.getCalculo().getContingenciasEmpresario()+"% "+this.getCalculo().calculoContigenciasComunes()+
            "\nDesempleo: "+this.getCalculo().getDesempleoEmpresario()+ "% "+this.getCalculo().calculoDesempleoEmpresario()+
            "\nFormacion: "+this.getCalculo().getFormacionEmpresaio()+"% "+this.getCalculo().calculoFormacionEmpresario()+
            "\nAccidentes de trabajo "+this.getCalculo().getAccidentesEmpresario()+"% "+this.getCalculo().calculoAccidentesEmpresario()+
            "\nFOGASA "+this.getCalculo().getFogasa()+"% "+this.getCalculo().calculoFogasa();
    String total = "Coste total para el empresario: "+getCosteTotalParaEmpresario()+"\nCoste total trabajador "+this.getCosteTotalTrabajador();
   if(  this.isEsExtra()){
       empresa = "EXTRA "+this.getMes()+"\n"+empresa;
   }
    String datos = empresa+ "\n"+trabajador+"\n"+fechaNomina+"\n"+importes+"\n"+descuentosTrabajador+"\n"+devengosYDeducciones+
            "\n"+pagosEmpresario+"\n"+total;
    
    }
   
    
    public void run() throws IOException{
        asignar();
        imprimirresultadoDatos();
    }
    
}