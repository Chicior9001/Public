package com.nauka.Kolokwium2;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TokenController {
    public final TokenService tokenService = new TokenService();
    private final List<JSONObject> tokens = new ArrayList<>();
    public Database database = new Database();
    AdminSocketService adminSocketService = new AdminSocketService(8081);

    public TokenController() throws IOException {
        // tokenService = new TokenService();
        adminSocketService.start();
    }

    @PostMapping("/register")
    public /*JSONObject*/ Object register() throws IOException {
        Token token = tokenService.generateToken();

        JSONObject obj = new JSONObject();
        obj.put("token", token.token());
        obj.put("creationTime", token.creationTime());
        obj.put("isActive", token.isActive());
        tokens.add(obj);

        return obj.get("token");
        /*return obj;*/
    }

    @GetMapping("/tokens")
    public List<String> getTokens() {
        // return tokens;
        /*return tokens.stream()
                .map(token -> Map.of(
                        "token", token.get("token"),
                        "createdAt", token.get("creationTime").toString(),
                        "isActive", token.get("isActive")
                ))
                .collect(Collectors.toList());*/
        List<String> tokenList = new ArrayList<>();
        for (JSONObject obj : tokens) {
            tokenList.add(obj.toString());
        }
        return tokenList;
    }
}
