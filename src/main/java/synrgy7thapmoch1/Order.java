package synrgy7thapmoch1;


import lombok.Getter;

@Getter
class Order{
    private  String menu;
    private  Integer qty;

    public Order(String menu, Integer qty) {
        this.menu = menu;
        this.qty = qty;
    }
}
