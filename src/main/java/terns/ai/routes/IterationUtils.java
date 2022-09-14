package terns.ai.routes;

import java.util.ArrayList;
import java.util.List;

public class IterationUtils {

    public List<OutputCsv> combineRoutes(List<Port> ports) {
        List<OutputCsv> toReturn = new ArrayList<>();
        int i = 0;
        int j = 1;
        while (i < ports.size() - 1) {
            while (j < ports.size()) {
                OutputCsv outputCsv = new OutputCsv();
                Port origin = ports.get(i);
                Port destination = ports.get(j);
                outputCsv.setOrigin(origin.getPort());
                outputCsv.setDestination(destination.getPort());
                toReturn.add(outputCsv);
                j++;
            }
            i++;
            j = i + 1;
        }
        return toReturn;
    }
}
