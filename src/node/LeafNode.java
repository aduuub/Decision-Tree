package node;

import java.util.List;

/**
 * Created by Adam on 25/03/17.
 */
public class LeafNode implements Node {

    private int attribute;
    private double probability;

    public LeafNode(int attribute, double probability) {
        this.attribute = attribute;
        this.probability = probability;
    }

    @Override
    public void report(String indent, List<String> attNames) {
        System.out.format("%sClass %s, prob=%f\n", indent, attNames.get(attribute), probability);
    }
}
