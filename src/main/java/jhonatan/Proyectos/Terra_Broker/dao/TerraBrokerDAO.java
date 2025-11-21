package jhonatan.Proyectos.Terra_Broker.dao;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;

@Repository
@Transactional
public class TerraBrokerDAO {
	
	@PersistenceContext
	private EntityManager em; 
	
	// USUARIOS
	public void altaUsuario(Usuario u) {
		em.persist(u);
	}
	
	public void modificarUsuario(Usuario u) {
		Usuario usuario = em.find(Usuario.class, u.getId());
		if (usuario != null) {
			usuario.setNombre(u.getNombre());
			usuario.setApellido(u.getApellido());
			usuario.setPais(u.getPais());
			usuario.setEmail(u.getEmail());

		} else {
			System.out.println("Usuario no encontrado con el ID proporcionado.");
		}
	}
	
	public Usuario consultaUsuario(Long id) {
		return em.find(Usuario.class, id);
	}

}
