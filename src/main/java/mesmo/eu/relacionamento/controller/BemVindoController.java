package mesmo.eu.relacionamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/olar")
public class BemVindoController
{
    @GetMapping
    public ResponseEntity<String> bemVindo()
    {
        return ResponseEntity.ok("Seja Bem-Vindes");
    }
}
