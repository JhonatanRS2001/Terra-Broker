package jhonatan.Proyectos.Terra_Broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jhonatan.Proyectos.Terra_Broker.modelo.Activo;

@Repository
public interface ActivoRepository extends JpaRepository<Activo, Long> {
	boolean existsByNombre(String nombre);
    
}