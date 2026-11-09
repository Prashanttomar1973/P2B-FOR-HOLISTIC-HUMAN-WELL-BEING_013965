package com.email.writer.Controllers;

import com.email.writer.Services.P2BGeneratorService;
import com.email.writer.Services.P2BEngineService; // Correct Import
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/generate")
public class P2BGeneratorController {

    @Autowired
    private P2BGeneratorService service;

    @Autowired
    private P2BEngineService p2bEngineService; // Injected Instance

    @PostMapping("/report")
    public ResponseEntity<?> getReport(@RequestBody Map<String, Object> formData) {
        // 1. AI Engine se Raw Response (Markdown/JSON) lena
        String rawResponse = service.generateReport(formData);

        try {
            // 2. Robust Cleaning: Extract pure JSON object
            String cleanedJson = rawResponse != null ? rawResponse.trim() : "";
            if (cleanedJson.contains("{") && cleanedJson.contains("}")) {
                int start = cleanedJson.indexOf('{');
                int end = cleanedJson.lastIndexOf('}');
                cleanedJson = cleanedJson.substring(start, end + 1).trim();
            } else if (cleanedJson.startsWith("```")) {
                cleanedJson = cleanedJson.replaceAll("^```(?:json)?|```$", "").trim();
            }

            // 3. Final JSON Parsing check
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(cleanedJson, Map.class);
            if (jsonMap.containsKey("error")) {
                return ResponseEntity.status(500).body("P2B Engine Sync Error: " + jsonMap.get("error"));
            }

            // 4. Data Extraction for DB Sync
            String domain = (String) formData.get("primaryDomain"); // Check frontend key
            if (domain == null) domain = (String) formData.getOrDefault("domain", "Neural_Analysis"); 
            
            String userQuery = (String) formData.getOrDefault("targetDomain", "Universal System Analysis");
            String userEmail = (String) formData.getOrDefault("email", "anonymous@p2b.ai");

            // 5. DATABASE SYNC (PostgreSQL + MongoDB)
            p2bEngineService.processUniversalRequest(domain, userQuery, cleanedJson, userEmail);

            return ResponseEntity.ok(jsonMap);

        } catch (Exception e) {
            // Useful for debugging in Postman
            return ResponseEntity.status(500).body("P2B Engine Sync Error: " + e.getMessage());
        }
    }
}