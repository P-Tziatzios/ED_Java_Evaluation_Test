package com.dterz.controllers;

import com.dterz.service.InfoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/info")
@CrossOrigin
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    /**
     * Returns the current Version of the components of this Application
     *
     * @return ResponseEntity<Map < String, Object>>
     */
    @GetMapping("{component}")
    public ResponseEntity<?> getVersion(@PathVariable("component") String component) {
        return ResponseEntity.ok(infoService.getVersion(component));
    }
}
