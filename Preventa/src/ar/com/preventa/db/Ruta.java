package ar.com.preventa.db;

public class Ruta {
	
	private int dia;
	private int vendedor;
	private Cliente cliente;
	private int pedidos;
	
	public int getPedidos() {
		return pedidos;
	}

	public void setPedidos(int pedidos) {
		this.pedidos = pedidos;
	}

	public int getDia() {
		return dia;
	}
	
	public void setDia(int dia) {
		this.dia = dia;
	}
	
	public int getVendedor() {
		return vendedor;
	}
	
	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
