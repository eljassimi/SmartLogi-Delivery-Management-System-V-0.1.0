package com.smartlogi.controller;

import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.service.ColisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    private final ColisService colisService;

    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @GetMapping
    public Page<ColisResponseDTO> getColis(@RequestParam(required = false) String statut,
                                           @RequestParam(required = false) String zoneId,
                                           @RequestParam(required = false) String ville,
                                           @RequestParam(required = false) String priorite,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestParam(defaultValue = "dateCreation") String sortBy,
                                           @RequestParam(defaultValue = "desc") String sortDir) {

        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? 20 : size;
        Sort.Direction direction = Sort.Direction.DESC;
        if ("asc".equalsIgnoreCase(sortDir)) {
            direction = Sort.Direction.ASC;
        }

        String safeSortBy = switch (sortBy) {
            case "statut", "priorite", "villeDestination", "dateCreation", "poids" -> sortBy;
            default -> "dateCreation";
        };

        Pageable pageable = PageRequest.of(safePage, Math.min(safeSize, 200), Sort.by(direction, safeSortBy));

        return colisService.search(statut,pageable);
    }

    @PostMapping
    public ColisResponseDTO createColis(@Valid @RequestBody ColisRequestDTO request) {
        return colisService.save(request);
    }

    @GetMapping("/{colisId}")
    public ColisResponseDTO getColisById(@PathVariable String colisId){
        return colisService.findById(colisId);
    }
}
