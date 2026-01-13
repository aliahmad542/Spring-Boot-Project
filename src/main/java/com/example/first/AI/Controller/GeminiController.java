package com.example.first.AI.Controller;

import com.example.first.AI.dto.GeminiRequestDTO;
import com.example.first.AI.Service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/suggest")
    public String suggest(@RequestBody GeminiRequestDTO request) throws Exception {
        return geminiService.suggest(request);
    }
}
