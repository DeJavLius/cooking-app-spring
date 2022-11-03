package teamproject.capstone.recipe.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.capstone.recipe.domain.api.CookRecipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@Slf4j
public class APIController {
    // /api/ac3c23441c1c4a1e9696/COOKRCP01/json/1/1061
    // 총 1 - 1061개

    @GetMapping("/")
    public String responseOpenAPI() {
        return null;
    }
}
