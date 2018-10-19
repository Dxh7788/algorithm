package algorithm;

/*-----------AVL树--------------*/

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongxiaohong on 2018/10/16 11:32
 */
public class AvlNode{
    //red-black tree links
    AvlNode parent;
    AvlNode left;
    AvlNode right;
    Integer value;

    public AvlNode() {
    }

    public AvlNode(Integer value) {
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
    public static AvlNode newAvlNode(Integer value) {
        return new AvlNode(value);
    }

    /**
     * 构建平衡二叉树
     * '原理就是插入数据
     *  第一阶段就---每次都要平衡一下
     *  第二阶段就---每次失衡了再平衡
     */
    /*static AvlNode treeify(AvlNode that){
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
    }*/
    /**
     * 需要进行旋转的最小树,进行右旋转
     * @param root
     * */
    public AvlNode rightRotate(AvlNode root){
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
    public AvlNode leftRotate(AvlNode root){
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
    AvlNode balancify(AvlNode root, AvlNode xp){
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
    public static void middle(AvlNode root, List<Integer> values){
        if (root!=null){
            middle(root.left,values);
            values.add(root.value);
            middle(root.right, values);
        }
    }
    /**
     * 后序输出
     * */
    public static void back(AvlNode root, List<Integer> values){
        if (root != null){
            back(root.left, values);
            back(root.right, values);
            values.add(root.value);
        }
    }
    /**
     * 后序输出
     * */
    public static void front(AvlNode root, List<Integer> values){
        if (root!=null) {
            values.add(root.value);
            front(root.left, values);
            front(root.right, values);
        }
    }
    /**
    * 放入元素
    * */
    public AvlNode add(Integer value, AvlNode root){
        //允许使用默认构造函数,直接调用
        if (root.value == null){
            root = null;
        }
        AvlNode x = new AvlNode(value);
        x.left = x.right = null;
        if (root == null){
            x.parent = null;
            root = x;
        }else {
            int val = x.value;
            int pv,dir;
            //遍历root
            for (AvlNode p = root;;){
                //加左树
                if ((pv = p.value) > val){
                    dir = -1;
                }//加右树
                else if (pv < val){
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
        return root;
    }
}
