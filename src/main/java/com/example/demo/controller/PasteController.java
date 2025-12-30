package com.example.demo.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Paste;
import com.example.demo.repository.PasteRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/pastes")
public class PasteController {
	
	private final PasteRepository repo;
	
	

    public PasteController(PasteRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<?> createPaste(@RequestBody Map<String, Object> body) {

        String content = (String) body.get("content");
        Integer ttl = (Integer) body.get("ttl_seconds");
        Integer maxViews = (Integer) body.get("max_views");

        if (content == null || content.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid content"));
        }

        long now = System.currentTimeMillis();

        Paste paste = new Paste();
        paste.setId(UUID.randomUUID().toString());
        paste.setContent(content);
        paste.setCreatedAt(now);
        paste.setExpiresAt(ttl != null ? now + ttl * 1000L : null);
        paste.setMaxViews(maxViews);
        paste.setViews(0);

        repo.save(paste);

        return ResponseEntity.ok(
            Map.of(
                "id", paste.getId(),
                "url", "http://localhost:8080/p/" + paste.getId()
            )
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaste(
            @PathVariable String id,
            HttpServletRequest request) {

        return repo.findById(id).map(paste -> {

            long now = com.example.demo.util.TimeUtil.now(request);

            // TTL check
            if (paste.getExpiresAt() != null && now > paste.getExpiresAt()) {
                return ResponseEntity
                        .status(404)
                        .body(Map.of("error", "Expired"));
            }

            // View limit check
            if (paste.getMaxViews() != null &&
                paste.getViews() >= paste.getMaxViews()) {

                return ResponseEntity
                        .status(404)
                        .body(Map.of("error", "View limit exceeded"));
            }

            // Increment views
            paste.setViews(paste.getViews() + 1);
            repo.save(paste);

            Integer remainingViews = paste.getMaxViews() == null
                    ? null
                    : paste.getMaxViews() - paste.getViews();

            // ✅ USE HashMap (allows nulls)
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("content", paste.getContent());
            response.put("remaining_views", remainingViews);
            response.put("expires_at", paste.getExpiresAt());

            return ResponseEntity.ok(response);

        }).orElse(
            ResponseEntity
                .status(404)
                .body(Map.of("error", "Not found"))
        );
    }
}