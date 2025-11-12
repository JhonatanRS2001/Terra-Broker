package jhonatan.Proyectos.Terra_Broker.modelo;

import jakarta.persistence.*;

@Entity
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cantidad;
    private Double precioPromedio;
    private Double valorActual;

    @ManyToOne
    @JoinColumn(name = "cartera_id")
    private Cartera cartera;

    @ManyToOne
    @JoinColumn(name = "activo_id")
    private Activo activo;
    
    public Posicion() {
    	
    }
    
    public Posicion(Cartera cartera, Activo activo, Double cantidad, Double precioPromedio) {
        this.cartera = cartera;
        this.activo = activo;
        this.cantidad = cantidad;
        this.precioPromedio = precioPromedio;
        this.valorActual = cantidad * activo.getPrecioActual();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioPromedio() {
		return precioPromedio;
	}

	public void setPrecioPromedio(Double precioPromedio) {
		this.precioPromedio = precioPromedio;
	}

	public Cartera getCartera() {
		return cartera;
	}

	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

	public Activo getActivo() {
		return activo;
	}

	public void setActivo(Activo activo) {
		this.activo = activo;
	}

	public Double getValorActual() {
		return valorActual;
	}

	public void setValorActual(Double valorActual) {
		this.valorActual = valorActual;
	}
	
	public void actualizarValorActual() {
        this.valorActual = this.cantidad * this.activo.getPrecioActual();
    }

	@Override
	public String toString() {
		return "Posicion [id=" + id + ", cantidad=" + cantidad + ", precioPromedio=" + precioPromedio + ", valorActual="
				+ valorActual + ", cartera=" + cartera + ", activo=" + activo + "]";
	}
}