package teamproject.capstone.recipe.utils.login.attributes;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.entity.user.UserEntity;
import teamproject.capstone.recipe.utils.values.Role;

import java.util.Map;

@Getter
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    private static final int GOOGLE_COM = 0;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }

    public UserEntity toAdminEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .role(Role.ADMIN)
                .build();
    }
}
