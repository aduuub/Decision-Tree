package node;

import main.Patient;

import java.util.List;

/**
 * Created by Adam on 25/03/17.
 */
public class LeafNode implements Node {

    private String outcome;
    private double probability;

    public LeafNode(String attribute, double probability) {
        this.outcome = attribute;
        this.probability = probability;
    }

    @Override
    public void report(String indent) {
        System.out.format("%sCategory %s, prob=%f\n", indent, outcome, probability);
    }

    @Override
    public String traverse(Patient patient, List<String> attributeNames) {
        return outcome;

    }
}
