package teamproject.capstone.recipe.utils.page;

import lombok.Getter;

import java.util.*;

@Getter
public class SearchWrapper {
    private final List<Search> searchList;
    private final Search name;
    private final Search detail;
    private final Search part;
    private final Search seq;
    private final Search way;

    public static class Builder {
        private final List<Search> searchList = new ArrayList<>();
        private Search name = Search.builder().build();
        private Search detail = Search.builder().build();
        private Search part = Search.builder().build();
        private Search seq = Search.builder().build();
        private Search way = Search.builder().build();

        public Builder name(String keyword) {
            if (!keyword.isEmpty()) {
                this.name = Search.builder().type("name").keyword(keyword).build();
                searchList.add(this.name);
            }
            return this;
        }

        public Builder detail(String keyword) {
            if (!keyword.isEmpty()) {
                this.detail = Search.builder().type("detail").keyword(keyword).build();
                searchList.add(this.detail);
            }
            return this;
        }

        public Builder part(String keyword) {
            if (!keyword.isEmpty()) {
                this.part = Search.builder().type("part").keyword(keyword).build();
                searchList.add(this.part);
            }
            return this;
        }

        public Builder seq(String keyword) {
            if (!keyword.equals("0")) {
                this.seq = Search.builder().type("seq").keyword(keyword).build();
                searchList.add(this.seq);
            }
            return this;
        }

        public Builder way(String keyword) {
            if (!keyword.isEmpty()) {
                this.way = Search.builder().type("way").keyword(keyword).build();
                searchList.add(this.way);
            }
            return this;
        }

        public SearchWrapper build() {
            return new SearchWrapper(this);
        }
    }

    private SearchWrapper(Builder builder) {
        searchList = builder.searchList;
        name = builder.name;
        detail = builder.detail;
        part = builder.part;
        seq = builder.seq;
        way = builder.way;
    }
}
