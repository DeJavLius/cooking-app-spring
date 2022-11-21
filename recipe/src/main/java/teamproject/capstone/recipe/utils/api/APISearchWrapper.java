package teamproject.capstone.recipe.utils.api;

import java.util.ArrayList;
import java.util.List;

public class APISearchWrapper {
    private final List<APISearch> apiSearchList;
    private final APISearch name;
    private final APISearch detail;
    private final APISearch part;
    private final APISearch seq;
    private final APISearch way;

    public static class Builder {
        private final List<APISearch> apiSearchList = new ArrayList<>();
        private APISearch name = APISearch.builder().build();
        private APISearch detail = APISearch.builder().build();
        private APISearch part = APISearch.builder().build();
        private APISearch seq = APISearch.builder().build();
        private APISearch way = APISearch.builder().build();

        public Builder name(String keyword) {
            if (!keyword.isEmpty()) {
                this.name = APISearch.builder().type("name").keyword(keyword).build();
                apiSearchList.add(this.name);
            }
            return this;
        }

        public Builder detail(String keyword) {
            if (!keyword.isEmpty()) {
                this.detail = APISearch.builder().type("detail").keyword(keyword).build();
                apiSearchList.add(this.detail);
            }
            return this;
        }

        public Builder part(String keyword) {
            if (!keyword.isEmpty()) {
                this.part = APISearch.builder().type("part").keyword(keyword).build();
                apiSearchList.add(this.part);
            }
            return this;
        }

        public Builder seq(String keyword) {
            if (!keyword.equals("0")) {
                this.seq = APISearch.builder().type("seq").keyword(keyword).build();
                apiSearchList.add(this.seq);
            }
            return this;
        }

        public Builder way(String keyword) {
            if (!keyword.isEmpty()) {
                this.way = APISearch.builder().type("way").keyword(keyword).build();
                apiSearchList.add(this.way);
            }
            return this;
        }

        public APISearchWrapper build() {
            return new APISearchWrapper(this);
        }
    }

    private APISearchWrapper(Builder builder) {
        apiSearchList = builder.apiSearchList;
        name = builder.name;
        detail = builder.detail;
        part = builder.part;
        seq = builder.seq;
        way = builder.way;
    }

    public List<APISearch> getApiSearchList() {
        return this.apiSearchList;
    }
}
