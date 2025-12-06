package com.example.apirest.controller;

import com.example.apirest.model.Curso;
import com.example.apirest.repository.CursoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Curso crearCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizarCurso(@PathVariable Long id, @RequestBody Curso cursoActualizado) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setNombre(cursoActualizado.getNombre());
                    curso.setDescripcion(cursoActualizado.getDescripcion());
                    curso.setFechaInicio(cursoActualizado.getFechaInicio());
                    curso.setFechaFin(cursoActualizado.getFechaFin());
                    return ResponseEntity.ok(cursoRepository.save(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/nombre")
    public List<Curso> buscarPorNombre(@RequestParam String nombre) {
        return cursoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @GetMapping("/buscar/fecha")
    public List<Curso> buscarPorFechaInicio(@RequestParam String fecha) {
        LocalDate fechaInicio = LocalDate.parse(fecha);
        return cursoRepository.findByFechaInicio(fechaInicio);
    }
}
