package node;

/**
 * Created by Adam on 24/03/17.
 */
public class ParentNode implements Node{
    private Node trueNode; // Left
    private Node falseNode; // Right
    private int attribute;

    public ParentNode(int attribute, Node trueNode, Node falseNode) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
        this.attribute = attribute;
    }

    public Node getTrueNode() {
        return trueNode;
    }

    public Node getFalseNode() {
        return falseNode;
    }

    public int getAttribute(){
        return attribute;
    }
}
