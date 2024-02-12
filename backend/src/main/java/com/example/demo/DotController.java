package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DotController {
    private final DotRepository dotRepository;
    private final AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public DotController(DotRepository dotRepository, AuthService authService) {
        this.dotRepository = dotRepository;
        this.authService = authService;
    }

    @PostMapping("api/dots")
    public Dot addDot(@RequestBody DotDTO dotDTO) {
        String login = dotDTO.getOwner();
        Dot dot = new Dot(dotDTO.getX(), dotDTO.getY(), Float.parseFloat(dotDTO.getR()));
        dot.setOwner(login);
        dotRepository.save(dot);
        logger.info("Точки x:{} y:{} r:{} login: {} пришли", dotDTO.getX(), dotDTO.getY(), dotDTO.getR(), login);

        return dot;
    }

    @GetMapping("api/dots")
    public List<Dot> getDots(@RequestParam String login ) {
        return dotRepository.getDotsByOwner(login);
    }
}