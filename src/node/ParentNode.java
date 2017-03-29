package node;

import java.util.List;

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

    @Override
    public void report(String indent, List<String> attNames){
        if(attribute < 0 || attribute >= attNames.size()){

            System.out.println("err");
        }

        System.out.format("%s%s = True:\n", indent, attNames.get(attribute));
        trueNode.report(indent+" ", attNames);

        System.out.format("%s%s = False:\n", indent, attNames.get(attribute));
        falseNode.report(indent+" ", attNames);
    }
}
