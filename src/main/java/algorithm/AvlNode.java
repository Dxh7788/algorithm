package algorithm;

/*-----------AVL树--------------*/

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongxiaohong on 2018/10/16 11:32
 */
public class AvlNode extends Node{
    //red-black tree links
    AvlNode parent;
    AvlNode left;
    AvlNode right;
    // needed to unlink next upon deletion
    AvlNode prev;
    boolean red;

    public AvlNode(Integer value, Node next) {
        super(value,next);
        this.value = value;
    }

    //返回根 root
    final AvlNode root(){
        for (AvlNode r = this, p;;){
            if ((p=r.parent)==null){
                return p;
            }
            r = p;
        }
    }
    /**
     * 想要构建二叉树首先要构建一个链表
     * 数组转链表
     * */
    static AvlNode replacementTreeNode(Integer[] args){
        if (args == null || args.length==0){
            return null;
        }
        AvlNode hd=null,tl=null;
        for (Integer ir : args){
            AvlNode p = treeNodefy(ir, null);
            if (tl == null){
                hd = p;
            }else {
                p.prev = tl;
                tl.next = p;
            }
            tl = p;
        }
        return hd;
    }

    private static AvlNode treeNodefy(Integer value, AvlNode next) {
        return new AvlNode(value,next);
    }

    /**
     * 构建平衡二叉树
     * '原理就是插入数据
     *  第一阶段就---每次都要平衡一下
     *  第二阶段就---每次失衡了再平衡
     */
    static AvlNode treeify(AvlNode that){
        AvlNode root = null;
        for(AvlNode x = that, next; x!=null; x= next){
            next = (AvlNode)x.next;
            x.left = x.right = null;
            if (root == null){
                x.parent = null;
                root = x;
            }else {
                int value = x.value;
                int pv,dir;
                //遍历root
                for (AvlNode p = root;;){
                    //加左树
                    if ((pv = p.value) > value){
                        dir = -1;
                    }//加右树
                    else if (pv < value){
                        dir = 1;
                    }//如果相等则进行其他比较
                    else {
                       dir = 0;
                    }
                    AvlNode xp = p;
                    if ((p = (dir <= 0) ? p.left:p.right)==null){
                        x.parent = xp;
                        if (dir <= 0){
                            xp.left = x;
                        }else {
                            xp.right = x;
                        }
                        //添加完成后进行平衡
                        root = balancify(root, x);
                        break;
                    }
                }
            }
        }
        return root;
    }

    /**
     * 左平衡,负责左树的平衡.先进行次级根树的左旋转,然后对根节点进行右旋转
     * @param root
     * */
    public AvlNode leftBalance(AvlNode root){
        AvlNode sRoot = root.left;
        sRoot = leftRotate(sRoot);
        root.left = sRoot;
        sRoot.parent = root;
        //对root进行左旋转
        root = rightRotate(root);
        return root;
    }
    /**
     * 右平衡,负责右树的平衡,先进行次级根树的右旋转,然后对根节点进行左旋转
     * @param root
     * */
    public AvlNode rightBalance(AvlNode root){
        AvlNode sRoot = root.right;
        sRoot = rightRotate(sRoot);
        root.right = sRoot;
        sRoot.parent = root;
        //对root进行左旋转
        root = leftRotate(root);
        return root;
    }
    /**
     * 需要进行旋转的最小树,进行右旋转
     * @param root
     * */
    public static AvlNode rightRotate(AvlNode root){
        AvlNode x = root,xp=root,xl=x.left;
        if (xl.right!=null){
            xp.left = xl.right;
            xl.right = xp;
        }
        xp.parent = xl;
        return xl;
    }
    /**
     * 需要进行旋转的最小树,进行左旋转
     * @param root
     * */
    public static AvlNode leftRotate(AvlNode root){
        AvlNode x = root, xp = root, xr = x.right;
        if (xr.left!=null){
            xp.right = xr.left;
            xr.left = xp;
        }
        xp.parent = xr;
        return xr;
    }
    /**
     * 计算树的深度
     * 无论左子树还是右子树只要能走下去就多一层
     * */
    public static int deepth(AvlNode root){
        if (root==null){
            return 0;
        }
        int deepth = 0;
        if (root.right!=null || root.left!=null){
            deepth++;
            int ld = deepth(root.left);
            int rd = deepth(root.right);
            deepth= deepth + Math.max(ld,rd);
        }
        return deepth;
    }
    /**
     * 平衡二叉树的左右二叉树也是平衡二叉树,所以可以采用递归方法
     * 找到最大执行单元.
     * 如果执行单左旋或者单右旋时,只需要执行leftRotate和rightRotate.
     * 如果是另外两种情况就需要找到最大旋转单元
     * */
    static AvlNode balancify(AvlNode root, AvlNode xp){
        //检查root的左右树差
        if (root == null || xp == null){
            return null;
        }
        int ld = deepth(root.left);
        int rd = deepth(root.right);
        //失去平衡
        if (Math.abs(ld-rd) == 2 ){
            //确定节点插入性质
            AvlNode xpp = xp.parent;
            if (ld > rd){
                //根节点的左子树添加
                if (xpp.left == xp){
                    //单右旋,直接对root操作
                    return rightRotate(root);
                }else if (xpp.right == xp){
                    //先对xpp左旋,再对root右旋
                    AvlNode xppr = xpp;
                    AvlNode nRoot = leftRotate(xpp);
                    xppr.right = nRoot;
                    return rightRotate(root);
                }
            }else {
                 if (xpp.right == xp){
                     //单左旋,直接对root操作
                     return leftRotate(root);
                 }else if (xpp.left == xp){
                     //先对xpp右旋,再对root左旋
                     AvlNode xppl = xpp;
                     AvlNode nRoot = rightRotate(xpp);
                     xppl.left = nRoot;
                     return leftRotate(root);
                }
            }
        }
        //没有失衡,则直接返回root
        return root;
    }

    /**
     * 中序输出
     * */
    public static List<Integer> middle(AvlNode root, List<Integer> values){
        if (root.left==null && root.right ==null){
            values.add(root.value);
            return values;
        }
        if ( root.left != null ){
            middle(root.left, values);
        }
        values.add(root.value);
        if (root.right != null){
            middle(root.right, values);
        }
        return values;
    }
    /**
     * 后序输出
     * */
    public static List<Integer> back(AvlNode root, List<Integer> values){
        if (root.left==null && root.right ==null){
            values.add(root.value);
            return values;
        }
        if ( root.left != null ){
            back(root.left, values);
        }
        if (root.right != null){
            back(root.right, values);
        }
        values.add(root.value);
        return values;
    }
    /**
     * 后序输出
     * */
    public static List<Integer> front(AvlNode root, List<Integer> values){
        if (root.left==null && root.right ==null){
            values.add(root.value);
            return values;
        }
        values.add(root.value);
        if ( root.left != null ){
            front(root.left, values);
        }
        if (root.right != null){
            front(root.right, values);
        }
        return values;
    }
    public static void main(String[] sargs) {
        Integer[] args = new Integer[]{80,60,90,83,95,93,97,100,70};
        AvlNode that = AvlNode.replacementTreeNode(args);
        AvlNode result = AvlNode.treeify(that);
        //前序查询
        List<Integer> values = new ArrayList<Integer>();
        //前序输出
        front(result, values);
        if (values != null && !values.isEmpty()){
            for (Integer value : values){
                System.out.print(value+" ");
            }
            System.out.println();
        }
        //中序输出
        values.clear();
        middle(result,values);
        if (values != null && !values.isEmpty()){
            for (Integer value : values){
                System.out.print(value+" ");
            }
            System.out.println();
        }
        //后序输出
        values.clear();
        back(result,values);
        if (values != null && !values.isEmpty()){
            for (Integer value : values){
                System.out.print(value+" ");
            }
        }
    }
}
