/*更改部分：
存入货物引入Product抽象类，将货物分为I,II,III，存入的上限不同
显示库存新增State类，分别显示可售卖数量和留存数量（1）
 */
import java.sql.*;

public class Warehouse {
    private Connection connection;

    public Warehouse() {
        try {
            //分别输入链接的数据库地址，用户名以及密码
            String url = " ";
            String user = " ";
            String password = " ";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(String item, int quantity, String type) {
        // SQL语句中使用组合唯一键，插入时如果存在相同名称和类型的记录，更新数量
        String sql = "INSERT INTO inventory (item_name, quantity, type) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, type);

            Product product;
            switch (type) {
                case "I":
                    product = new I(type, item, quantity);
                    break;
                case "II":
                    product = new II(type, item, quantity);
                    break;
                case "III":
                    product = new III(type, item, quantity);
                    break;
                default:
                    System.out.println("无效的类型!");
                    return;
            }

            // 检查是否超过上限
            if (!product.isOverflow()) { // 只有在没有超过上限时才执行插入
                pstmt.executeUpdate();
            } else {
                System.out.println("该物品添加失败，请重新添加！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 取出货物
    public void removeItem(String item, String itemType, int quantity, User currentUser) {
        String sql = "SELECT quantity FROM inventory WHERE item_name = ? AND type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item);
            pstmt.setString(2, itemType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // 仓库中存在该货物
                int currentQuantity = rs.getInt("quantity"); // 库存数量
                if (currentQuantity < quantity) {
                    System.out.println("库存不足，只能取出 " + currentQuantity + " 个 " + item);
                    // 创建订单
                    createOrder(currentUser, item, currentQuantity);
                    removeCompleteItem(item, itemType);
                } else if (currentQuantity == quantity) {
                    createOrder(currentUser, item, quantity);
                    removeCompleteItem(item, itemType);
                } else {
                    createOrder(currentUser, item, quantity);
                    updateItemQuantity(item, itemType, currentQuantity - quantity);
                    System.out.println("成功取出 " + quantity + " 个 " + item);
                }
            } else { // 仓库中不存在该货物
                System.out.println("库存中没有该类型的 " + item + " 物品。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createOrder(User user, String item, int quantity) {
        // 创建订单并打印订单信息
        String orderNumber = generateOrderNumber();
        Order order = new Order(user.getName(), orderNumber, item, quantity);
        System.out.println("订单已创建: " + order);
    }

    // 生成订单编号的简单方法
    private static String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis(); // 生成一个基于当前时间戳的唯一订单编号
    }


    //显示已取完
    private void removeCompleteItem(String item,String itemType) {
        String sql = "DELETE FROM inventory WHERE item_name = ? AND type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item);
            pstmt.setString(2, itemType);
            pstmt.executeUpdate();//修改数据库中的数据
            System.out.println("成功取出该货物，已取完 " + item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新库存
    private void updateItemQuantity(String item, String itemType,int quantity) {
        String sql = "UPDATE inventory SET quantity = ? WHERE item_name = ? AND type = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 设置参数
            pstmt.setInt(1, quantity);
            pstmt.setString(2, item);
            pstmt.setString(3, itemType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查看库存
    public void viewInventory() {
        String sql = "SELECT * FROM inventory";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) { // 判断是否有结果
                System.out.println("库存为空。");
                return;
            }
            System.out.println("当前库存：");
            while (rs.next()) {
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                String type = rs.getString("type");
                System.out.println("物品: " + itemName + ", 数量: " + quantity+"， 类型："+type);
                State state=new State();
                state.showSale(quantity);
                state.showStore();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
