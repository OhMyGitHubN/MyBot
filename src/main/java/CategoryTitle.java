import java.io.Serializable;

public enum CategoryTitle {

    PRODUCTS("Продукты"),
    ENTERTAINMENTS("Развлечения"),
    FARE("Проезд"),
    CAR("Автомобиль"),
    CREDIT("Кредит"),
    HEALTH("Здоровье"),
    RENT("Аренда"),
    MOBILE_CONNECTION("Связь"),
    COMMUNAL_PAYMENTS("Коммуналка"),
    INTERNET("Интернет"),
    CLOTHES("Одежда"),
    OTHER("Прочее");

    /*public String[] categoryList = {"Продукты", "продукты", "Развелечения", "развелечения", "Проезд", "проезд",
            "Автомобиль", "автомобиль", "Кредит", "кредит","Здоровье", "здоровье", "Аренда", "аренда",
            "Коммуналка", "коммуналка", "Связь", "связь", "Интернет", "интернет", "Прочее", "прочее", "Одежда", "одежда"};*/

    private static CategoryTitle categoryTitleInstance;
    private String title;

    private CategoryTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    /*public static Object whatCategory(String string, boolean flag) {
        for (CategoryTitle t : CategoryTitle.values()) {
            if (string.contains(t.title) || string.contains(t.title.toLowerCase())) {
                //System.out.println(t.title);
                return t.title;
            } else {
                flag = false;
                return flag;
            }
        }

    }*/

}
