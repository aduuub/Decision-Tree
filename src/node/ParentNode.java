package node;

import main.Patient;
import util.TreeBuilder;

import java.util.List;

/**
 * Created by Adam on 24/03/17.
 */
public class ParentNode implements Node {
    private Node trueNode; // Left
    private Node falseNode; // Right
    private String attribute;

    public ParentNode(String attribute, Node trueNode, Node falseNode) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
        this.attribute = attribute;
    }

    @Override
    public void report(String indent) {
        System.out.format("%s%s = True:\n", indent, attribute);
        trueNode.report(indent + TreeBuilder.INDENT_SPACING);

        System.out.format("%s%s = False:\n", indent, attribute);
        falseNode.report(indent + TreeBuilder.INDENT_SPACING);
    }

    @Override
    public String traverse(Patient patient, List<String> attributeNames) {
        if (attributeNames.contains(attribute)) {
            int index = attributeNames.indexOf(attribute);
            boolean result = patient.getAttribute(index);
            if(result)
                return trueNode.traverse(patient, attributeNames);
            else
                return falseNode.traverse(patient, attributeNames);

        } else {
            throw new IllegalArgumentException("Attribute names does not contain this attribute");
        }
    }
}
