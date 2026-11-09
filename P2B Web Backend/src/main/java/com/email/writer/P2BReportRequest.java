package com.email.writer;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class P2BReportRequest {
    // High performance model available on the Groq account
    private String model = "openai/gpt-oss-120b";
    private List<Map<String, String>> messages;

    // Low temperature (0.1 - 0.2) ensure karta hai ki JSON format break na ho
    private double temperature = 0.2;

    // Optional: Max tokens set kar sakte hain agar report bahut lambi ho
    private int max_tokens = 4096;
}