/*假设至少需要留存1个。
设计两个接口，分别显示可出售和留存。
定义类State，同时实现两个接口，显示留存和可出售数量
 */
interface OnSale{
    void showSale(int quantity);
}
interface Store{
    void showStore();
}
public class State implements OnSale,Store{
    public void showSale(int totalQuantity){
        if(totalQuantity> 1){
            System.out.println("出售："+(totalQuantity-1)+"个");
        }else{
            System.out.println("库存为1个，不能出售");
        }
    }
    public void showStore(){
        System.out.println("留存：1个");
    }
}
