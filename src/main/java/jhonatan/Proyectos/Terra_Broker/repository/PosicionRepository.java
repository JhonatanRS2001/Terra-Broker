package jhonatan.Proyectos.Terra_Broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jhonatan.Proyectos.Terra_Broker.modelo.Posicion;

public interface PosicionRepository extends JpaRepository<Posicion, Long> {
	
}
