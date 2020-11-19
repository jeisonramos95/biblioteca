package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.aplicacion.comando.ComandoLibro;
import com.ceiba.biblioteca.aplicacion.fabrica.FabricaLibro;
import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import org.springframework.context.annotation.ScopeMetadata;

import java.util.Calendar;
import java.util.Date;

public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
    public static final String EL_LIBRO_NO_SE_PUEDE_PRESTAR = "El libro solo se puede usar en la biblioteca";

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;
    private final FabricaLibro fabricaLibro;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
        this.fabricaLibro = new FabricaLibro();
    }

    public Prestamo prestar(String isbn, String nombreUsuario, ComandoLibro comandoLibro) {
        // Regla #3
        if(PalabraPalindroma(comandoLibro.getIsbn())){
            throw new PrestamoException(EL_LIBRO_NO_SE_PUEDE_PRESTAR);
        }
        // Reglas #4 y #5
        Prestamo prestamo = Obtenerprestamo(comandoLibro, nombreUsuario);
        this.repositorioPrestamo.agregar(prestamo);
        return prestamo;
        }

    public  Prestamo Obtenerprestamo (ComandoLibro comandoLibro, String nombreUsuario ){
        Libro libro = this.fabricaLibro.crearLibro(comandoLibro);

        Date fechaEntregaMaxima = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String cadena= libro.getIsbn();  //guarda la cadena
        int largo=cadena.length(), sum=0;  //guarda largo de cadena

        for(int i=0; i<largo; i++){  //recorre la cadena
            if (Character.isDigit(cadena.charAt(i))){  //si el caracter es digito
                int sum1 = cadena.charAt(i) - 48 ;
                sum = sum + sum1;
            }
        }
        if( sum > 30){
            int day = 0;
            int count = 1;
            while(count < 15){
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                day = calendar.get(Calendar.DAY_OF_WEEK);
                if (day != 1){
                    count++;
                }
            }
            if(day == 1) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            fechaEntregaMaxima = calendar.getTime();
        } else {

            fechaEntregaMaxima = null;
        }

        Prestamo prestamo = new Prestamo(new Date (), libro,  fechaEntregaMaxima,  nombreUsuario);
        return prestamo;
    }
    public boolean PalabraPalindroma(String isbn){
        int inc = 0;
        int des = isbn.length()-1;
        boolean bError = false;

        while ((inc<des) && (!bError)){

            if (isbn.charAt(inc)==isbn.charAt(des)){
                inc++;
                des--;
            } else {
                bError = true;
            }
        }
        return bError;
    }

    public boolean esPrestado(String isbn) {
        return this.repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn)!= null;
    }
}
