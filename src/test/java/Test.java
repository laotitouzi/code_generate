/**
 * @Author Han, Tixiang
 * @Create 2016/5/30
 */
public class Test {

    public static void  main(String[] args){
      String path=  Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.print(path);
    }
}
