package ar.com.preventa.db;

public class Producto {

	private String  codigo;
	private String  descripcion;
	private Integer subrubro;
	private Double  stock;
	private Integer marca;
	private Double  comision;
	private String  fechainicio;
	private String  fechafin;
	private Double  comision2;
	private Integer uxb;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getSubrubro() {
		return subrubro;
	}
	public void setSubrubro(Integer subrubro) {
		this.subrubro = subrubro;
	}
	public Double getStock() {
		return stock;
	}
	public void setStock(Double stock) {
		this.stock = stock;
	}
	public Integer getMarca() {
		return marca;
	}
	public void setMarca(Integer marca) {
		this.marca = marca;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public String getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}
	public String getFechafin() {
		return fechafin;
	}
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}
	public Double getComision2() {
		return comision2;
	}
	public void setComision2(Double comision2) {
		this.comision2 = comision2;
	}
	public Integer getUxb() {
		return uxb;
	}
	public void setUxb(Integer uxb) {
		this.uxb = uxb;
	}
}