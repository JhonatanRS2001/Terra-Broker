package jhonatan.Proyectos.Terra_Broker.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private int edad;
    private String pais;
    
    @Column(unique = true)
    private String email;
    private String password;
    private Double saldo=0.00;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Cartera cartera;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;
    
    public Usuario() {
    	
    }
    
    public Usuario(String nombre, String apellido, String pais, String email, String password, int edad) {
    	this.nombre=nombre;
    	this.apellido=apellido;
    	this.pais=pais;
    	this.email=email;
    	this.password=password;
    	this.edad=edad;
    	this.saldo=0.00;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Cartera getCartera() {
		return cartera;
	}

	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}
	
	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", pais=" + pais + ", email="
				+ email + ", password=" + password + ", saldo=" + saldo + ", cartera=" + cartera + ", transacciones="
				+ transacciones + "]";
	}   	
}