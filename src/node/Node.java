package node;

import java.util.List;

/**
 * Created by Adam on 25/03/17.
 */
public interface Node {

    /**
     * Print the node to the console and it recursively calls on the report
     * @param indent
     * @param attName
     */
    void report(String indent, List<String> attName);
}
