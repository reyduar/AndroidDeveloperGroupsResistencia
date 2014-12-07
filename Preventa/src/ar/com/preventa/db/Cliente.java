package ar.com.preventa.db;

import java.util.ArrayList;
import java.util.List;

import org.metawidget.inspector.annotation.UiComesAfter;
import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.annotation.UiSection;

public class Cliente {
	private String  codigo;
	private String  comercio;
	private String  nombre;
	private String  domicilio;
	private String  telefono;
	private String  tipocontribuyente;
	private String  cuit;
	private int     condicionventa;
	private double  saldo;
	private int     vendedor;
	private String  ciudad;
	private String  provincia;	
	private int     renglones;
	private List<Ruta> rutas = new ArrayList<Ruta>();      
	
	
	
	public void addRuta(Ruta ruta){
		rutas.add(ruta);
	}
	
	
	public List<Ruta> rutas()
	{
		return rutas;
	}
	
	@UiSection("Datos Personales")
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@UiHidden
	public String getComercio() {
		return comercio;
	}
	
	public void setComercio(String comercio) {
		this.comercio = comercio;
	}
	
	@UiLabel("Razon Social")
	@UiComesAfter("codigo")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@UiComesAfter("nombre")
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	@UiComesAfter("domicilio")
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@UiSection("Datos Comerciales")
	@UiComesAfter("telefono")
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
	public int getCondicionventa() {
		return condicionventa;
	}
	
	public void setCondicionventa(int condicionventa) {
		this.condicionventa = condicionventa;
	}
	
	@UiComesAfter("condicionventa")
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	
	
	@UiHidden
	public int getVendedor() {
		return vendedor;
	}
	
	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}
	
	@UiHidden
	public String getCiudad() {
		return ciudad;
	}
	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	@UiHidden
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	@UiHidden
	public int getRenglones() {
		return renglones;
	}
	
	public void setRenglones(int renglones) {
		this.renglones = renglones;
	}
	
	public String toString() {
		return "[" + codigo + ";" + nombre + "]";
	}
}
