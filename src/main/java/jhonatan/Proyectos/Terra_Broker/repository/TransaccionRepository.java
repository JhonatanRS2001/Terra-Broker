package jhonatan.Proyectos.Terra_Broker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jhonatan.Proyectos.Terra_Broker.modelo.Transaccion;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByUsuarioOrderByFechaAsc(Usuario usuario);
}