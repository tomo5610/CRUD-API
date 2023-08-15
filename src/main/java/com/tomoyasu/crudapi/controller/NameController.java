package com.tomoyasu.crudapi.controller;

import com.tomoyasu.crudapi.NameCreateForm;
import com.tomoyasu.crudapi.ResourceNotFoundException;
import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.service.NameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class NameController {
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    @GetMapping("/names")
    public List<NameResponse> names() {
        List<Name> names = nameService.findAll();
        List<NameResponse> response = names.stream().map(NameResponse::new).toList();
        return response;
    }

    @GetMapping("/names/{id}")
    public NameResponse getNameById(@PathVariable("id") int id) {
        Name name = nameService.findById(id);
        return new NameResponse(name);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(
            ResourceNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/names")
    public ResponseEntity<NameResponse> createName(@RequestBody NameCreateForm nameCreateForm, HttpServletRequest request) {
        Name name = nameService.createName(nameCreateForm);

        NameResponse nameResponse = new NameResponse(name);
        URI url = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .path("/names/{id}")
                .buildAndExpand(name.getId())
                .toUri();
        return ResponseEntity.created(url).body(nameResponse);
    }
}
