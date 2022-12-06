package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.utils.api.json.FavoriteRecipe;
import teamproject.capstone.recipe.service.recipe.FavoriteService;
import teamproject.capstone.recipe.service.recipe.OpenRecipeFavoriteService;
import teamproject.capstone.recipe.service.user.UserService;

@RequestMapping("/api/v1/ajax")
@RequiredArgsConstructor
@Slf4j
@Controller
public class FavoriteAPIController {
    private final UserService userService;
    private final OpenRecipeFavoriteService openRecipeFavoriteService;
    private final FavoriteService favoriteService;

    @PostMapping("/favorite")
    public String webOneFavoriteRecipe(FavoriteRecipe favoriteRecipe, Model model) {
        boolean isFavorite = false;

        if (userService.isUserExist(favoriteRecipe.getUserEmail())) {
            Favorite result = openRecipeFavoriteService.provideFavorite(favoriteRecipe.getUserEmail(), favoriteRecipe.getRecipeSeq());

            if (favoriteService.isFavoriteNotExist(result)) {
                favoriteService.create(result);
                isFavorite = true;
            } else {
                favoriteService.delete(favoriteRecipe.getUserEmail(), favoriteRecipe.getRecipeSeq());
            }
        }

        log.info("check is Favorite : {}", isFavorite);

        model.addAttribute("isFavorite", isFavorite);
        return "recipe/recipeDetail :: #favoriteCheck";
    }
}
