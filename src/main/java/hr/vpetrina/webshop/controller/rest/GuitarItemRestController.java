package hr.vpetrina.webshop.controller.rest;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.service.GuitarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rest/guitars")
public class GuitarItemRestController {

    private final GuitarService service;

    public GuitarItemRestController(GuitarService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GuitarItemDto>> getAll() {
        List<GuitarItemDto> guitarItemDtos = service.getAll();

        if (guitarItemDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(guitarItemDtos, HttpStatus.OK);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<GuitarItemDto> getById(@PathVariable Integer id) {
        Optional<GuitarItemDto> guitarItemDto = service.getById(id);

        return guitarItemDto
                .map(itemDto -> new ResponseEntity<>(itemDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/query/{query}")
    public ResponseEntity<List<GuitarItemDto>> filterByName(@PathVariable String query) {
        return ResponseEntity.ok(service.filterByName(query));
    }

    @PostMapping("/add")
    public ResponseEntity<GuitarItemDto> add(@RequestBody GuitarItemDto dto) {
        return ResponseEntity.ok(service.insert(dto));
    }
}
