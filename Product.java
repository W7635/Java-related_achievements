/*按照提示输入货物的名字，数量，类型（I,II,III类），
增加抽象类Product，根据类型，返回不同值，
I类一次性最多存入3件，
II类一次性最多存入5件，
III类一次性最多存入10件。
 */

public abstract class Product {
    String type;
    String item;
    int quantity;

    public Product(String type, String item, int quantity) {
        this.type = type;
        this.item = item;
        this.quantity = quantity;
    }
    // 检查是否超过最大存入数量的抽象方法
    public abstract boolean isOverflow();
}

class I extends Product {
    public I(String type, String item, int quantity) {
        super(type, item, quantity);
    }

    @Override
    public boolean isOverflow() {
        if (quantity > 3) {
            System.out.println("超过上限，一次性最多只能存入3件");
            return true; // 表示超过上限
        } else {
            System.out.println("存入成功，数量为 " + quantity);
            return false; // 表示没超过上限
        }
    }
}

class II extends Product {
    public II(String type, String item, int quantity) {
        super(type, item, quantity);
    }

    @Override
    public boolean isOverflow() {
        if (quantity > 5) {
            System.out.println("超过上限，一次性最多只能存入5件");
            return true;
        } else {
            System.out.println("存入成功，数量为 " + quantity);
            return false;
        }
    }
}

class III extends Product {
    public III(String type, String item, int quantity) {
        super(type, item, quantity);
    }

    @Override
    public boolean isOverflow() {
        if (quantity > 10) {
            System.out.println("超过上限，一次性最多只能存入10件");
            return true;
        } else {
            System.out.println("存入成功，数量为 " + quantity);
            return false;
        }
    }
}



