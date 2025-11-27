package jhonatan.Proyectos.Terra_Broker.util;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jhonatan.Proyectos.Terra_Broker.modelo.Activo;
import jhonatan.Proyectos.Terra_Broker.modelo.Cartera;
import jhonatan.Proyectos.Terra_Broker.modelo.Posicion;
import jhonatan.Proyectos.Terra_Broker.modelo.Transaccion;
import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;
import jhonatan.Proyectos.Terra_Broker.repository.ActivoRepository;
import jhonatan.Proyectos.Terra_Broker.repository.CarteraRepository;
import jhonatan.Proyectos.Terra_Broker.repository.PosicionRepository;
import jhonatan.Proyectos.Terra_Broker.repository.UsuarioRepository;

@Service
public class CarteraService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActivoRepository activoRepository;

    @Autowired
    private CarteraRepository carteraRepository;

    @Autowired
    private PosicionRepository posicionRepository;

    @Transactional
    public void comprarActivo(Long usuarioId, Long activoId, double cantidad) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new Exception("Usuario no encontrado"));

        Activo activo = activoRepository.findById(activoId).orElseThrow(() -> new Exception("Activo no encontrado"));

        double totalCompra = cantidad * activo.getPrecioActual();

        if (usuario.getSaldo() < totalCompra) {
            throw new Exception("Saldo insuficiente");
        }

        // OBTENER CARTERA USUARIO
        Cartera cartera = usuario.getCartera();
        if (cartera == null) {
            cartera = new Cartera();
            cartera.setUsuario(usuario);
            usuario.setCartera(cartera);
        }

        // BUSCAR SI EXISTE POSICION
        Posicion posicion = cartera.getPosiciones() != null? cartera.getPosiciones().stream().filter(p -> p.getActivo().getId().equals(activoId))
                    .findFirst().orElse(null): null;

        if (posicion != null) {
            // ACTUALIZAR CANTIDAD Y PRECIO PROMEDIO
            double nuevaCantidad = posicion.getCantidad() + cantidad;
            double nuevoPrecioPromedio = (posicion.getCantidad() * posicion.getPrecioPromedio() + totalCompra) / nuevaCantidad;

            posicion.setCantidad(nuevaCantidad);
            posicion.setPrecioPromedio(nuevoPrecioPromedio);
            posicion.actualizarValores(); 

            posicionRepository.save(posicion);
        } else {
            // CREAR NUEVA POSICION
            posicion = new Posicion(cartera, activo, cantidad, activo.getPrecioActual());
            posicion.actualizarValores();

            if (cartera.getPosiciones() == null) {
                cartera.setPosiciones(new ArrayList<>());
            }
            cartera.getPosiciones().add(posicion);
            posicionRepository.save(posicion);
        }

        // ACTUALIZAR SALDO USUARIO
        usuario.setSaldo(usuario.getSaldo() - totalCompra);
        
        //REGISTRAR TRANSACCIÓN COMPRA
        Transaccion transaccion = new Transaccion(usuario,activo,"COMPRA",cantidad,activo.getPrecioActual());
        usuario.getTransacciones().add(transaccion);

        usuarioRepository.save(usuario);
        carteraRepository.save(cartera);
    }
    
    @Transactional
    public void venderActivo(Long usuarioId, Long activoId, Double cantidadAVender) throws Exception {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new Exception("Usuario no encontrado"));

        Cartera cartera = usuario.getCartera();

        Posicion posicion = cartera.getPosiciones().stream().filter(p -> p.getActivo().getId().equals(activoId)).findFirst()
                .orElseThrow(() -> new Exception("No tienes este activo en tu cartera"));

        if (cantidadAVender == null || cantidadAVender <= 0)
            throw new Exception("La cantidad debe ser mayor que 0");

        if (posicion.getCantidad() < cantidadAVender)
            throw new Exception("No puedes vender más cantidad de la que tienes");

        //PRECIO ACTUAL
        Double precioActual = posicion.getActivo().getPrecioActual();

        //CALCULAR VALORES REALES
        Double totalVenta = precioActual * cantidadAVender;
        Double costoOriginal = posicion.getPrecioPromedio() * cantidadAVender;
        Double ganancia = totalVenta - costoOriginal;

        //ACTUALIZAR POSICION
        Double nuevaCantidad = posicion.getCantidad() - cantidadAVender;

        if (nuevaCantidad <= 0) {
            cartera.getPosiciones().remove(posicion);
        } else {
            posicion.setCantidad(nuevaCantidad);
        }

        // SUMAR SOLO EL TOTAL DE LA VENTA
        usuario.setSaldo(usuario.getSaldo() + totalVenta);
        
        //REGISTRAR TRANSACCIÓN DE VENTA
        Transaccion tx = new Transaccion(usuario, posicion.getActivo(), "VENTA", cantidadAVender, precioActual, totalVenta, ganancia);
        usuario.getTransacciones().add(tx);

        usuarioRepository.save(usuario);
        carteraRepository.save(cartera);
    }

    // MÉTODO PARA ACTUALIZAR TODAS LAS POSICIONES DE UNA CARTERA
    @Transactional
    public void actualizarPosicionesCartera(Long usuarioId) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new Exception("Usuario no encontrado"));

        Cartera cartera = usuario.getCartera();
        if (cartera != null && cartera.getPosiciones() != null) {
            for (Posicion p : cartera.getPosiciones()) {
                p.actualizarValores();
                posicionRepository.save(p);
            }
        }
    }
}