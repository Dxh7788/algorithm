package algorithm;

/**
 * @author dongxiaohong on 2018/10/16 15:11
 */
public class Node {
    Integer value;
    Node next;

    public Node(Integer value, Node next) {
        this.value = value;
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
