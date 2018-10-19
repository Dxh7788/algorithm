package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongxiaohong on 2018/10/19 09:20
 */
public class AvlNodeTest {
    public static void main(String[] sargs) {
        Integer[] args = new Integer[]{80,60,90,83,95,93,97,100,55};
        AvlNode root = new AvlNode();
        for (Integer arg:args){
            root = root.add(arg, root);
        }
        List<Integer> values = new ArrayList<Integer>();
        //前序输出
        AvlNode.front(root, values);
        if (values != null && !values.isEmpty()){
            for (Integer value : values){
                System.out.print(value+" ");
            }
            System.out.println();
        }
        //中序输出
        values.clear();
        root.middle(root,values);
        if (values != null && !values.isEmpty()){
            for (Integer value : values){
                System.out.print(value+" ");
            }
            System.out.println();
        }
        //后序输出
        values.clear();
        root.back(root,values);
        if (values != null && !values.isEmpty()){
            for (Integer value : values){
                System.out.print(value+" ");
            }
        }
    }
}
