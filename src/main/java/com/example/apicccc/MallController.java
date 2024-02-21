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


    @GetMapping("/fechainauguracion")
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


    @GetMapping("/cinema")
    public List<CentroComercial> getCentrosConSalasDeCine() {
        List<CentroComercial> centrosComerciales = repository.findByCinema();
        return centrosComerciales;
    }


    @GetMapping("/localesrestauracion")
    public List<Object[]> getCentrosConLocalesRestauracion() {
        List<Object[]> result = repository.findNombresCentrosConLocalesRestauracion();
        return result;
    }


    @GetMapping("/localesModa")
    public List<Object[]> getCentrosConLocalesModa() {
        List<Object[]> result = repository.findNombresCentrosConLocalesModa();
        return result;
    }

    @GetMapping("/busquedaSalasCine/{numSalasCine}")
    public List<Object[]> getNombreByNumeroSalasCine(@PathVariable Integer numSalasCine) {
        List<Object[]> result = repository.findNombreByNumeroSalasCine(numSalasCine);
        return result;
    }

    @GetMapping("/aforo")
    public List<String> getCentrosComercialesOrderedByAforo() {
        List<String> result = repository.findNombreAforoOrderedByAforoDesc();
        return result;
    }

    @GetMapping("/capadidadparking")
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


    @PostMapping("/create")
    public ResponseEntity<CentroComercial> nuevo(@RequestBody CentroComercial centroComercial) {
        try {
            CentroComercial nuevoCentroComercial = repository.save(centroComercial);
            return new ResponseEntity<>(nuevoCentroComercial, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CentroComercial> delete(@PathVariable Integer id, @RequestParam Integer token) {
        if (security.validateToken(id, token)) {
            CentroComercial centroComercial = repository.findByIdAndToken(id, token);

            if (centroComercial != null) {
                repository.deleteById(id);
                return new ResponseEntity<>(centroComercial, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("update/{id}")
    public ResponseEntity<CentroComercial> put(@PathVariable Integer id, @RequestBody CentroComercial centroNuevo, @RequestParam Integer token) {

        if (!security.validateToken(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CentroComercial centroComercial = repository.findByIdAndToken(id, token);

        if (centroComercial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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

        return new ResponseEntity<>(repository.save(centroComercial), HttpStatus.OK);
    }


    }
/*POST http://localhost:8080/api
Content-Type: application/json

{"nombre": "aDAdaDASDASDASDASDAd",
  "direccion": "werwewerwer",
  "tamaño": 9.345,
  "telefono": 952348764,
  "localesrestauracion": "Gourmet Bistro, Mediterranean Delights, Sushi Fusion, Parisian Brasserie, The Steakhouse, Pizzeria Italia, International Flavors Café, Fresh & Healthy, Tapas Lounge, Fusion Grill, Gelato Paradise, Tea House Elegance, Wine & Dine Lounge, Coffee Artistry\r\n",
  "localesmoda": "'Fashion Boutique, Trendy Styles, Urban Chic, Haute Couture, Denim Den, Vintage Vogue, Glamour Haven, Streetwear Emporium, Classy Attire, Boho Beauty, Sports Couture, Tailored Elegance, Runway Ready, Casual Comfort, Timeless Threads'\r\n",
  "recreativos": false,
  "numerosalascine": 0,
  "aforo": 679,
  "capacidadaparcamiento": 265,
  "horario": "9:30-11:00",
  "fechainauguracion": "19-09-1979",
  "descripcion": "Bienvenido al icónico Centro Comercial El Corte Inglés, un referente de compras y estilo de vida en [ciudad]. Descubre un mundo de exclusividad y variedad con nuestras reconocidas tiendas de moda, desde las últimas tendencias hasta prestigiosas marcas internacionales. Sumérgete en la experiencia gastronómica en nuestros elegantes restaurantes y cafeterías. El Corte Inglés es más que un centro comercial; es un destino completo con áreas recreativas, eventos culturales y una amplia oferta de servicios. Explora nuestras plantas dedicadas a productos de alta calidad, desde moda y belleza hasta tecnología y hogar. Con su elegante arquitectura y un ambiente vibrante, El Corte Inglés te invita a vivir una experiencia de compras única y exclusiva.",
  "token": 234234
}

*/


