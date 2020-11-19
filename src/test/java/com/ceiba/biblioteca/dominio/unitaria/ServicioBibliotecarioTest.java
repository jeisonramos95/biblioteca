
package com.ceiba.biblioteca.dominio.unitaria;


import com.ceiba.biblioteca.aplicacion.comando.ComandoLibro;
import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBibliotecarioTest {

    @Test
    public void libroYaEstaPrestadoTest() {

        // arrange
        Libro libro = new LibroTestDataBuilder().build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertTrue(existeProducto);
    }

    @Test
    public void libroNoEstaPrestadoTest() {

        // arrange
        Libro libro = new LibroTestDataBuilder().build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertFalse(existeProducto);
    }

    @Test
    public void espalabraPalindroma() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        String palabra = "A151A";

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean esPalindroma = servicioBibliotecario.palabraPalindroma(palabra);

        // assert
        assertFalse(esPalindroma);
    }

    @Test
    public void noEspalabraPalindroma() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        String palabra = "oTRo34isbndf";

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean esPalindroma = servicioBibliotecario.palabraPalindroma(palabra);

        // assert
        assertTrue(esPalindroma);
    }

    @Test
    public void fechaEntregaMaximaEsNull() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        String nombreUsuario = "nombreFake";

        Libro libro = new LibroTestDataBuilder().build();
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        Prestamo prestamo = servicioBibliotecario.obtenerprestamo(libro, nombreUsuario);

        // assert
        assertNull(prestamo.getFechaEntregaMaxima());
    }

    @Test
    public void fechaEntregaMaximaNoEsNull() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        String nombreUsuario = "nombreFake";
        Libro libro = new LibroTestDataBuilder().buildMayor30();
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        Prestamo prestamo = servicioBibliotecario.obtenerprestamo(libro, nombreUsuario);

        // assert
        assertNotNull(prestamo.getFechaEntregaMaxima());
    }

    @Test
    public void estringEsNumerico() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        String stringNumerico = "2";
        // act
        boolean esNumerico = servicioBibliotecario.isNumeric(stringNumerico);

        // assert
        assertTrue(esNumerico);
    }

    @Test
    public void estringNoEsNumerico() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        String stringNumerico = "A";
        // act
        boolean esNumerico = servicioBibliotecario.isNumeric(stringNumerico);

        // assert
        assertFalse(esNumerico);
    }

    @Test (expected = PrestamoException.class)
    public void errorCuandoEncuentraIsbnPalindromo() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        String isbn = "fakeIsbn";
        String nombreUsuario = "";
        Libro libro = new LibroTestDataBuilder().buildPalindromo();
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        when(repositorioLibro.obtenerPorIsbn(isbn)).thenReturn(libro);

        //act
        servicioBibliotecario.prestar(isbn, nombreUsuario);
    }

    @Test
    public void prestamoContieneFechaMaximaNull() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        String isbn = "fakeIsbn";
        String nombreUsuario = "";
        Libro libro = new LibroTestDataBuilder().build();
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        when(repositorioLibro.obtenerPorIsbn(isbn)).thenReturn(libro);

        //act
        Prestamo prestamo = servicioBibliotecario.prestar(isbn, nombreUsuario);
        assertNull(prestamo.getFechaEntregaMaxima());
    }

    @Test
    public void prestamoContieneFechaMaximaNoNull() {
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        String isbn = "fakeIsbn";
        String nombreUsuario = "";
        Libro libro = new LibroTestDataBuilder().buildMayor30();
        when(repositorioLibro.obtenerPorIsbn(isbn)).thenReturn(libro);
        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        //act
        Prestamo prestamo = servicioBibliotecario.prestar(isbn, nombreUsuario);
        assertNotNull(prestamo.getFechaEntregaMaxima());
    }
}

