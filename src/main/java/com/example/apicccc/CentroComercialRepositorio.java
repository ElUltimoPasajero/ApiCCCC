package com.example.apicccc;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface CentroComercialRepositorio extends JpaRepository<CentroComercial, Integer> {

    List<CentroComercial> findByRecreativos(boolean b);

    @Query("SELECT c FROM CentroComercial c WHERE c.numerosalascine > 0")
    List<CentroComercial> findByCinema();

    @Query("SELECT c.nombre, c.localesrestauracion FROM CentroComercial c")
    List<Object[]> findNombresCentrosConLocalesRestauracion();

    @Query("SELECT c.nombre, c.localesmoda FROM CentroComercial c")
    List<Object[]> findNombresCentrosConLocalesModa();

    @Query("SELECT c.nombre, c.aforo, c.capacidadaparcamiento FROM CentroComercial c")
    List<Object[]> findNombreAforoCapacidadParking();

    @Query("SELECT c.nombre FROM CentroComercial c WHERE c.numerosalascine >= :numSalasCine")
    List<Object[]> findNombreByNumeroSalasCine(@PathVariable Integer numSalasCine);

    @Query("SELECT CONCAT(c.nombre, ' - Aforo: ', c.aforo) FROM CentroComercial c ORDER BY c.aforo DESC")
    List<String> findNombreAforoOrderedByAforoDesc();
}
