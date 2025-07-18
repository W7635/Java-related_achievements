/*建立Order类，根据User形成订单
只有管理员能取出货物并建立订单
客户取出则报错
 */
public class Order {
    String userName;
    String orderNumber;
    String item;
    int quantity;

    // 构造函数
    public Order(String userName, String orderNumber, String item, int quantity) {
        this.userName = userName;
        this.orderNumber = orderNumber;
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "管理员: " + userName + ", 订单号: " + orderNumber + ", 物品: " + item + ", 数量: " + quantity;
    }
}

