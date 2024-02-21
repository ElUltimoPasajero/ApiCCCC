package com.example.apicccc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private CentroComercialRepositorio centroComercialRepository;

    public Boolean validateToken(Integer centroComercialId, Integer providedToken) {
        // Buscar el CentroComercial por su ID y token
        return centroComercialRepository.findByIdAndToken(centroComercialId, providedToken)!=null;
    }
}

