package com.ceiba.biblioteca.aplicacion.manejadores.prestamo;

import com.ceiba.biblioteca.aplicacion.comando.ComandoLibro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorGenerarPrestamo {

    private final ServicioBibliotecario servicioBibliotecario;

    public ManejadorGenerarPrestamo(ServicioBibliotecario servicioBibliotecario) {
        this.servicioBibliotecario = servicioBibliotecario;
    }

    @Transactional
    public Prestamo ejecutar(String isbn, String nombreUsuario, ComandoLibro comandoLibro) {
        return this.servicioBibliotecario.prestar( isbn,  nombreUsuario, comandoLibro);
    }
}
