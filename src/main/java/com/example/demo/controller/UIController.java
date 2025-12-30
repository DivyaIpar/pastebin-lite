package com.example.demo.controller;

import com.example.demo.entity.Paste;
import com.example.demo.repository.PasteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class UIController {

    private final PasteRepository repo;

    public UIController(PasteRepository repo) {
        this.repo = repo;
    }

    // ✅ Home page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ✅ FORM SUBMIT HANDLER
    @PostMapping("/create")
    public String createPaste(
            @RequestParam String content,
            @RequestParam(required = false) Integer ttl,
            @RequestParam(required = false) Integer maxViews,
            Model model) {

        long now = System.currentTimeMillis();

        Paste paste = new Paste();
        paste.setId(UUID.randomUUID().toString());
        paste.setContent(content);
        paste.setCreatedAt(now);
        paste.setExpiresAt(ttl != null ? now + ttl * 1000L : null);
        paste.setMaxViews(maxViews);
        paste.setViews(0);

        repo.save(paste);

        // pass URL to success page
        model.addAttribute("url", "/p/" + paste.getId());

        return "success";
    }
}
