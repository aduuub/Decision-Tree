package node;

/**
 * Created by Adam on 25/03/17.
 */
public class LeafNode implements Node{

    private int attribute;
    private double probability;

    public LeafNode(int attribute, double probability) {
        this.attribute = attribute;
        this.probability = probability;
    }

    public int getAttribute() {
        return attribute;
    }

    public double getProbability() {
        return probability;
    }
}
