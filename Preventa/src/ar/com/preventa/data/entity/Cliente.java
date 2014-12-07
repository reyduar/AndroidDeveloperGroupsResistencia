package ar.com.preventa.data.entity;

import org.metawidget.inspector.annotation.UiComesAfter;
import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.annotation.UiSection;

public class Cliente {
	
	//DATOS PERSONALES
	private String codigo;
	private String razonsocial;
	private String domicilio;
	private String provincia;
	private String localidad;
	private String telefono;
	
	//DATOS COMERCIALES
	private String tipocontribuyente;
	private String cuit;
	private String condicionventa;
	private String saldo;
	private String limitecredito;
	
	
	@UiSection("Datos Personales")
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@UiLabel("Razon Social")
	@UiComesAfter("codigo")
	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
    
	@UiComesAfter("razonsocial")
	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
    
    @UiComesAfter("domicilio") 
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
    
    @UiComesAfter("provincia")
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
    @UiComesAfter("localidad")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
    
    @UiSection("Datos Comerciales")
    @UiComesAfter("telefono")
    @UiLabel("Tipo Contribuyente")
	public String getTipocontribuyente() {
		return tipocontribuyente;
	}

	public void setTipocontribuyente(String tipocontribuyente) {
		this.tipocontribuyente = tipocontribuyente;
	}
    
    @UiComesAfter("tipocontribuyente")
	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
    
    @UiComesAfter("cuit")
    @UiLabel("Condicion Venta")
	public String getCondicionventa() {
		return condicionventa;
	}

	public void setCondicionventa(String condicionventa) {
		this.condicionventa = condicionventa;
	}
    
    @UiComesAfter("condicionventa")
	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
    
    @UiComesAfter("saldo")
    @UiLabel("Limite Credito")
	public String getLimitecredito() {
		return limitecredito;
	}

	public void setLimitecredito(String limitecredito) {
		this.limitecredito = limitecredito;
	}
}
