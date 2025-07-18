/*更改部分：
先显示displayID。
菜单新增5.更换身份，选择后。重新显示displayID。
若选择管理员，则引用admin类，
若选择用户，则引用client类。
取出货物增加User类，根据不同用户类型，给出不同返回值
 */
import java.util.Scanner;

public class Main {
    private static User currentUser; // 当前用户

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        Scanner scanner = new Scanner(System.in);
        int choice;
        displayID(scanner);
        do {
            if(currentUser==null) {
                displayID(scanner);
            }
            // 当前用户的角色
            if (currentUser != null) {
                currentUser.displayRole();
            }
            displayMenu();
            choice = scanner.nextInt();
            executeChoice(choice, warehouse, scanner);
            System.out.println(" ");
        } while (choice != 4);

        scanner.close();
    }

    public static void displayID(Scanner scanner) {
        System.out.println("=====身份选择=====");
        System.out.println("1. 管理员");
        System.out.println("2. 客户");
        System.out.println("=================");
        System.out.print("请选择身份 (1-2): ");
        int idChoice = scanner.nextInt();
        if (idChoice == 1) {
            System.out.print("请输入管理员姓名: ");
            String name = scanner.next();
            currentUser = new Admin(name);
        } else if (idChoice == 2) {
            System.out.print("请输入客户姓名: ");
            String name = scanner.next();
            currentUser = new Client(name);
        } else {
            System.out.println("身份选择错误！");
        }
    }

    public static void displayMenu() {
        System.out.println("=====仓储物资管理系统=====");
        System.out.println("1. 存入货物");
        System.out.println("2. 取出货物");
        System.out.println("3. 查看库存");
        System.out.println("4. 退出");
        System.out.println("5. 更换身份");
        System.out.println("=======================");
        System.out.print("请选择操作 (1-5): ");
    }

    public static void executeChoice(int choice, Warehouse warehouse, Scanner scanner) {
        switch (choice) {
            case 1:
                System.out.print("请输入货物类型 (I, II, III): ");
                String typeToAdd = scanner.next();
                System.out.print("输入物品名称: ");
                String itemNameToAdd = scanner.next();
                System.out.print("输入数量: ");
                int quantityToAdd = scanner.nextInt();
                warehouse.addItem(itemNameToAdd, quantityToAdd, typeToAdd);
                break;
            case 2:
                // 取出货物
                if (currentUser instanceof Admin) { // 检查当前用户是否为管理员
                    System.out.print("输入物品名称: ");
                    String itemNameToRemove = scanner.next();
                    System.out.print("输入物品类型: "); // 添加提示输入物品类型
                    String itemTypeToRemove = scanner.next(); // 获取物品类型
                    System.out.print("输入数量: ");
                    int quantityToRemove = scanner.nextInt();

                    warehouse.removeItem(itemNameToRemove, itemTypeToRemove, quantityToRemove,currentUser);
                } else {
                    System.out.println("只有管理员可以取出货物。");
                }
                break;

            case 3:
                warehouse.viewInventory();
                break;
            case 4:
                System.out.println("退出系统。");
                break;
            case 5:
                displayID(scanner);
                break;
            default:
                System.out.println("选择操作错误！请重新选择：");
                break;
        }
    }

}


