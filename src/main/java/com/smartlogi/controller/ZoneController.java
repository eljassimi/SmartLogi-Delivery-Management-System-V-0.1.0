package com.smartlogi.controller;

import com.smartlogi.dto.zone.ZoneRequestDTO;
import com.smartlogi.dto.zone.ZoneResponseDTO;
import com.smartlogi.service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public List<ZoneResponseDTO> getAllZones() {
        return zoneService.findAll();
    }

    @GetMapping("/{zoneId}")
    public ZoneResponseDTO getZoneById(@PathVariable String zoneId) {
        return zoneService.findById(zoneId);
    }

    @PostMapping
    public ZoneResponseDTO createZone(@Valid @RequestBody ZoneRequestDTO request) {
        return zoneService.save(request);
    }

    @PutMapping("/{zoneId}")
    public ZoneResponseDTO updateZone(@PathVariable String zoneId, @Valid @RequestBody ZoneRequestDTO request) {
        return zoneService.update(zoneId, request);
    }

    @DeleteMapping("/{zoneId}")
    public void deleteZone(@PathVariable String zoneId) {
        zoneService.delete(zoneId);
    }
}


