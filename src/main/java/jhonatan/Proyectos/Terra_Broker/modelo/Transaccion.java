package jhonatan.Proyectos.Terra_Broker.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private String tipo; // "COMPRA" o "VENTA"
    private Double cantidad;
    private Double precioUnitario;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "activo_id")
    private Activo activo;
    
    public Transaccion() {
    	
    }
    
    public Transaccion(Usuario usuario, Activo activo, String tipo, Double cantidad, Double precioUnitario, Double total) {
        this.usuario = usuario;
        this.activo = activo;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.fecha = LocalDateTime.now(); // Se asigna la fecha al crear la transacción
    }
    
    public Transaccion(Usuario usuario, Activo activo, String tipo, Double cantidad, Double precioUnitario) {
        this.usuario = usuario;
        this.activo = activo;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = 0.00;
        this.fecha = LocalDateTime.now(); // Se asigna la fecha al crear la transacción
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Activo getActivo() {
		return activo;
	}

	public void setActivo(Activo activo) {
		this.activo = activo;
	}
	
	@Override
	public String toString() {
		return "Transaccion [id=" + id + ", fecha=" + fecha + ", tipo=" + tipo + ", cantidad=" + cantidad
				+ ", precioUnitario=" + precioUnitario + ", total=" + total + ", usuario=" + usuario + ", activo="
				+ activo + "]";
	}   	
}