package jhonatan.Proyectos.Terra_Broker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jhonatan.Proyectos.Terra_Broker.modelo.Usuario;
import jhonatan.Proyectos.Terra_Broker.repository.UsuarioRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correoElectronico));

        return new MyUserDetails(usuario);
    }
}