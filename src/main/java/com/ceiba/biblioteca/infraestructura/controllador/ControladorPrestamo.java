package com.ceiba.biblioteca.infraestructura.controllador;

import com.ceiba.biblioteca.aplicacion.comando.ComandoLibro;
import com.ceiba.biblioteca.aplicacion.manejadores.prestamo.ManejadorGenerarPrestamo;
import com.ceiba.biblioteca.aplicacion.manejadores.prestamo.ManejadorObtenerPrestamo;
import com.ceiba.biblioteca.dominio.Prestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class ControladorPrestamo {
    @Autowired
    private final ManejadorObtenerPrestamo manejadorObtenerPrestamo;

    @Autowired
    private final ManejadorGenerarPrestamo manejadorGenerarPrestamo;

    public ControladorPrestamo(ManejadorObtenerPrestamo manejadorObtenerPrestamo, ManejadorGenerarPrestamo manejadorGenerarPrestamo) {
        this.manejadorObtenerPrestamo = manejadorObtenerPrestamo;
        this.manejadorGenerarPrestamo = manejadorGenerarPrestamo;
    }

    @PostMapping("/{isbn}/{nombreUsuario}")
    public Prestamo prestar(@PathVariable(name = "isbn") String isbn, @PathVariable(name = "nombreUsuario") String nombreUsuario) {
        return this.manejadorGenerarPrestamo.ejecutar(isbn, nombreUsuario);
    }

    @GetMapping("/{isbn}")
    public Prestamo obtenerLibroPrestadoPorIsbn(@PathVariable(name = "isbn") String isbn) {
        return this.manejadorObtenerPrestamo.ejecutar(isbn);
    }
}
