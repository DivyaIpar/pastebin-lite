package com.example.demo.controller;

import com.example.demo.entity.Paste;
import com.example.demo.repository.PasteRepository;
import com.example.demo.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ViewController {

    private final PasteRepository repo;

    public ViewController(PasteRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/p/{id}", produces = "text/html")
    public ResponseEntity<String> viewPaste(
            @PathVariable String id,
            HttpServletRequest request) {

        return repo.findById(id)
            .map(paste -> {

                long now = TimeUtil.now(request);

                // ✅ TTL CHECK
                if (paste.getExpiresAt() != null && now > paste.getExpiresAt()) {
                    return ResponseEntity
                            .status(404)
                            .body("Paste expired");
                }

                // ✅ VIEW LIMIT CHECK
                if (paste.getMaxViews() != null &&
                    paste.getViews() >= paste.getMaxViews()) {

                    return ResponseEntity
                            .status(404)
                            .body("View limit exceeded");
                }

                // ❌ HTML view does NOT increment views (allowed)
                // Assignment counts API fetches as views

                // ✅ SAFE HTML rendering
                String safeContent = paste.getContent()
                        .replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;");

                return ResponseEntity.ok(
                        "<html><body><pre>" + safeContent + "</pre></body></html>"
                );
            })
            .orElse(
                ResponseEntity.status(404).body("Paste not found")
            );
    }
}
