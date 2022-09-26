package com.entra21.grupo1.controller;

import com.entra21.grupo1.model.dto.FilmeDTO;
import com.entra21.grupo1.model.dto.FilmeDTOWithDetails;
import com.entra21.grupo1.model.dto.FilmePayLoadDTO;
import com.entra21.grupo1.model.dto.GenerosFilmeDTO;
import com.entra21.grupo1.view.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeRestController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    public List<FilmeDTO> getAllFilmes(@RequestParam(name = "genero", required = false) String genero, @RequestParam(name = "nota", required = false) Double nota) {
        return filmeService.getAll(genero, nota);
    }

    @GetMapping("/{nome}")
    public FilmeDTOWithDetails getFilmeByNome(@PathVariable(name = "nome") String nome){
        return filmeService.getByNome(nome);
    }

    @PostMapping
    public FilmeDTOWithDetails addFilme(@RequestBody FilmePayLoadDTO newfilme) {
        return filmeService.saveFilme(newfilme);
    }

    @PostMapping("/generos")
    public void addGenerosdeFilme(@RequestBody GenerosFilmeDTO generos){
        filmeService.addGeneros(generos);
    }

    @PutMapping
    public FilmeDTOWithDetails updateFilme(@RequestBody FilmeDTOWithDetails filmeDTO){
        return filmeService.update(filmeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteFilme(@PathVariable(name = "id") Long id){
        filmeService.delete(id);
    }
}
