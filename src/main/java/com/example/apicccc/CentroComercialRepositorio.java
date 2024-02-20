package com.example.apicccc;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface CentroComercialRepositorio extends JpaRepository<CentroComercial, Integer> {

    List<CentroComercial> findByRecreativos(boolean b);

    CentroComercial findByIdAndToken(Integer id, Integer token);

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


/*   @GetMapping("/order/{criterio}")
    public List<String> getCentrosComercialesOrderedByAforo() {
        List<String> result = repository.findNombreAforoOrderedByAforoDesc();
        return result;
    }*/


/*    @GetMapping("/filter/{param}/{value}")
    public List<CentroComercial> getCentrosComercialesConRecreativos(@RequestParam(name = "recreativos", defaultValue = "true") boolean recreativos) {
        return repository.findByRecreativos(recreativos);
    }*/
