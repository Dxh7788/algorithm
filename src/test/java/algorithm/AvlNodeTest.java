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
        //找到右子树最小值
        //AvlNode node = root.minRightAvlNode(root.right);
        //AvlNode node = root.replaceAvlNode(root.right);
        AvlNode remove = root.remove(93);
        //System.out.println(node.value);
        System.out.println(remove!=null?remove.value:"");
    }
}
