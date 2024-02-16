package com.example.apicccc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class MallController {


    @Autowired
    private CentroComercialRepositorio repository;

    @Autowired
    private SecurityService security;


    @GetMapping("/")
    public List<CentroComercial> getAll() {

        return repository.findAll();

    }


    @GetMapping("/{id}")
    public ResponseEntity<CentroComercial> get(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recreativos")
    public List<CentroComercial> getCentrosComercialesConRecreativos(@RequestParam(name = "recreativos", defaultValue = "true") boolean recreativos) {
        return repository.findByRecreativos(recreativos);
    }


    @GetMapping("/nombre")
    public List<String> getNombresCentrosComerciales() {
        List<CentroComercial> centrosComerciales = repository.findAll();
        List<String> nombresCentrosComerciales = new ArrayList<>();

        for (CentroComercial centroComercial : centrosComerciales) {
            nombresCentrosComerciales.add(centroComercial.getNombre());
        }

        return nombresCentrosComerciales;
    }


    @GetMapping("/nombrecccc-fechainauguracion")
    public List<CentroComercialDTO> getInfoCentrosComerciales() {
        List<CentroComercial> centrosComerciales = repository.findAll();
        List<CentroComercialDTO> infoCentrosComerciales = new ArrayList<>();

        for (CentroComercial centroComercial : centrosComerciales) {
            String nombre = centroComercial.getNombre();
            String fechaInauguracion = centroComercial.getFechainauguracion();

            CentroComercialDTO dto = new CentroComercialDTO(nombre, fechaInauguracion);
            infoCentrosComerciales.add(dto);
        }

        return infoCentrosComerciales;
    }

    
    @GetMapping("/with-cinema")
    public List<CentroComercial> getCentrosConSalasDeCine() {
        List<CentroComercial> centrosComerciales = repository.findByCinema();
        return centrosComerciales;
    }


    @GetMapping("/namecccc-localesrestauracion")
    public List<Object[]> getCentrosConLocalesRestauracion() {
        List<Object[]> result = repository.findNombresCentrosConLocalesRestauracion();
        return result;
    }


    @GetMapping("/namecccc-localesModa")
    public List<Object[]> getCentrosConLocalesModa() {
        List<Object[]> result = repository.findNombresCentrosConLocalesModa();
        return result;
    }

    @GetMapping("/namecccc-by-numero-salas-cine")
    public List<Object[]> getNombreByNumeroSalasCine(@RequestParam Integer numSalasCine) {
        List<Object[]> result = repository.findNombreByNumeroSalasCine(numSalasCine);
        return result;
    }


    @GetMapping("/namecccc-aforo-capacidadaparking")
    public List<Object[]> getNombreAforoCapacidadParking() {
        List<Object[]> result = repository.findNombreAforoCapacidadParking();

        List<Object[]> modifiedResult = new ArrayList<>();
        for (Object[] row : result) {
            String nombre = (String) row[0];
            Integer aforo = (Integer) row[1];
            Integer capacidadAparcamiento = (Integer) row[2];

            Object[] modifiedRow = {nombre, "Aforo: " + aforo, "Capacidad: " + capacidadAparcamiento};
            modifiedResult.add(modifiedRow);
        }

        return modifiedResult;
    }


    @PostMapping("/")
    public ResponseEntity<CentroComercial> nuevo(@RequestBody CentroComercial centroComercial, @RequestParam String token) {
        if (security.validateToken(token)) {
            return new ResponseEntity<CentroComercial>(repository.save(centroComercial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping("/{id}")

    public ResponseEntity<CentroComercial> delete(@PathVariable Integer id, @RequestParam String token) {

        ResponseEntity<CentroComercial> respuesta = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (security.validateToken(token)) {
            CentroComercial salida = new CentroComercial();
            if (repository.existsById(id)) {
                salida = repository.findById(id).get();
                repository.deleteById(id);
                respuesta = new ResponseEntity<CentroComercial>(salida, HttpStatus.OK);
            } else {
                respuesta = new ResponseEntity<CentroComercial>(salida, HttpStatus.NOT_FOUND);
            }
        }
        return respuesta;
    }

    @PutMapping("/{id}")

    public ResponseEntity<CentroComercial> put(@PathVariable Integer id, @RequestBody CentroComercial centroNuevo, @RequestParam String token) {

        if (!security.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            var centroComercial = new CentroComercial();

            var optionalCentroComercial = repository.findById(id);

            if (optionalCentroComercial.isEmpty()) {
                centroComercial = centroNuevo;
            } else {
                centroComercial = optionalCentroComercial.get();
                centroComercial.setNombre(centroNuevo.getNombre());
                centroComercial.setDireccion(centroNuevo.getDireccion());
                centroComercial.setTamaño(centroNuevo.getTamaño());
                centroComercial.setTelefono(centroNuevo.getTelefono());
                centroComercial.setLocalesrestauracion(centroNuevo.getLocalesrestauracion());
                centroComercial.setLocalesmoda(centroNuevo.getLocalesmoda());
                centroComercial.setRecreativos(centroNuevo.getRecreativos());
                centroComercial.setAforo(centroNuevo.getAforo());
                centroComercial.setCapacidadaparcamiento(centroNuevo.getCapacidadaparcamiento());
                centroComercial.setHorario(centroNuevo.getHorario());
                centroComercial.setFechainauguracion(centroNuevo.getFechainauguracion());
                centroComercial.setDescripcion(centroNuevo.getDescripcion());

            }

            return new ResponseEntity<CentroComercial>(repository.save(centroComercial), HttpStatus.OK);
        }

    }


}
