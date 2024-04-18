package synrgy7thapmoch1;

import lombok.Getter;

@Getter
class Order {
    private final String menu;
    private final Integer qty;

    public Order(String menu, Integer qty) {
        this.menu = menu;
        this.qty = qty;
    }
}