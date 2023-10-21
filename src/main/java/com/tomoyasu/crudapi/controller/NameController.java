package com.tomoyasu.crudapi.controller;

import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.form.NameCreateForm;
import com.tomoyasu.crudapi.form.NameUpdateForm;
import com.tomoyasu.crudapi.service.NameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Name> createName(@RequestBody @Valid NameCreateForm nameCreateForm, UriComponentsBuilder uriBuilder) {
        YearMonth birth = YearMonth.parse(nameCreateForm.getBirth());
        Name name = nameService.createName(nameCreateForm.getName(), birth);
        URI url = uriBuilder
                .path("/names/" + name.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(name);
    }

    @PatchMapping("names/{id}")
    public ResponseEntity<Map<String, String>> updateName(@PathVariable int id, @RequestBody @Valid NameUpdateForm nameUpdateForm) throws Exception {
        YearMonth birth = YearMonth.parse(nameUpdateForm.getBirth());
        nameService.updateName(id, nameUpdateForm.getName(), birth);
        return ResponseEntity.ok(Map.of("message", "successfully updated"));
    }

    @DeleteMapping("names/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable int id) {
        nameService.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "successfully delete"));
    }

}
