package jhonatan.Proyectos.Terra_Broker.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartera {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
	private LocalDateTime fechaCreacion;
	
	@OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	
	@OneToMany(mappedBy = "cartera", cascade = CascadeType.ALL)
	private List<Posicion> posiciones = new ArrayList<>();
	
	public Cartera() {
		this.fechaCreacion=LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Posicion> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(List<Posicion> posiciones) {
		this.posiciones = posiciones;
	}

	@Override
	public String toString() {
		return "Cartera [id=" + id + ", fechaCreacion=" + fechaCreacion + ", usuario=" + usuario + ", posiciones="
				+ posiciones + "]";
	}	
}
