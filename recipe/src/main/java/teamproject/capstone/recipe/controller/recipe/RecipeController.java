package teamproject.capstone.recipe.controller.recipe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/recipes")
@Controller
public class RecipeController {

    @GetMapping
    public String showAllRecipes() {
        return "";
    }

    @GetMapping("search")
    public String searchRecipes() {
        return "";
    }
}
