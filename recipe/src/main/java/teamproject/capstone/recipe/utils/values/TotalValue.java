package teamproject.capstone.recipe.utils.values;

public final class TotalValue {
    private static int totalCount = 0;

    private TotalValue() {
        throw new AssertionError();
    }

    public static void setTotalCount(int count) {
        totalCount = count;
    }

    public static int getTotalCount() {
        return totalCount;
    }
}
