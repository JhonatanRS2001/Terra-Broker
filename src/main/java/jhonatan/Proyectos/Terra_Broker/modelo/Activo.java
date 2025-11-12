package jhonatan.Proyectos.Terra_Broker.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Activo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String simbolo;
    private String nombre;
    private String imagen;
    private Double precioActual;

    @OneToMany(mappedBy = "activo", cascade = CascadeType.ALL)
    private List<Posicion> posiciones;

    @OneToMany(mappedBy = "activo", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;
    
    public Activo() {
    	
    }
    
    public Activo(String simbolo, String nombre, String imagen, Double precioActual) {
    	this.simbolo=simbolo;
    	this.nombre=nombre;
    	this.imagen=imagen;
    	this.precioActual=precioActual;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Double getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(Double precioActual) {
		this.precioActual = precioActual;
	}

	public List<Posicion> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(List<Posicion> posiciones) {
		this.posiciones = posiciones;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}

	@Override
	public String toString() {
		return "Activo [id=" + id + ", simbolo=" + simbolo + ", nombre=" + nombre + ", imagen=" + imagen
				+ ", precioActual=" + precioActual + ", posiciones=" + posiciones + ", transacciones=" + transacciones
				+ "]";
	}  	
}