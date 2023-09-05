package com.tomoyasu.crudapi.controller;

import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.form.NameCreateForm;
import com.tomoyasu.crudapi.service.NameService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
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

    @PostMapping("/names")
    public ResponseEntity<Name> createName(@RequestBody @Valid NameCreateForm nameCreateForm, HttpServletRequest request) {
        Name name = nameService.createName(nameCreateForm.getName(), nameCreateForm.getBirth());

        URI url = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .path("/{id}")
                .buildAndExpand(name.getId())
                .toUri();
        return ResponseEntity.created(url).body(name);
    }
}
