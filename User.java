/*设计抽象类User包括用户和管理员，
抽象方法展示身份和名字，
*/
public abstract class User {
    String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract void displayRole();
}

class Admin extends User {
    public Admin(String name) {
        super(name);
    }

    @Override
    void displayRole() {
        System.out.println("当前身份: 管理员 (" + name + ")");
    }
}

class Client extends User {
    public Client(String name) {
        super(name);
    }

    @Override
    void displayRole() {
        System.out.println("当前身份: 客户 (" + name + ")");
    }
}