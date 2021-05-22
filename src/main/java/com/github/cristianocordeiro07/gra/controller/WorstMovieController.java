package com.github.cristianocordeiro07.gra.controller;

import com.github.cristianocordeiro07.gra.model.dto.ProducerAwardsIntervalDTO;
import com.github.cristianocordeiro07.gra.service.WorstMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("worstMovie")
public class WorstMovieController {

    private final WorstMovieService service;

    @Autowired
    public WorstMovieController(WorstMovieService service) {
        this.service = service;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @GetMapping("/producerAwardsInterval")
    public ResponseEntity<ProducerAwardsIntervalDTO> getAwardsInverval() {
        try {
            return new ResponseEntity<>(service.getProducerAwardsInverval(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
