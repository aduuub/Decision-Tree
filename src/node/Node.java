package node;

import main.Patient;

import java.util.List;

/**
 * Created by Adam on 25/03/17.
 */
public interface Node {

    /**
     * Print the node to the console and it recursively calls on the report
     * @param indent
     */
    void report(String indent);


    String traverse(Patient patient, List<String> attributeNames);
}
