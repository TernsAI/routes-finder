package terns.ai.routes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class IterationUtils {

    public List<OutputCsv> combineRoutes(List<Port> ports) {
        List<OutputCsv> toReturn = new ArrayList<>();
        int i = 0;
        int j = 1;
        int k = 0;
        while (i < ports.size() - 1) {
            while (j < ports.size()) {
                OutputCsv outputCsv = new OutputCsv();
                OutputCsv outputCsvInverse = new OutputCsv();
                Port origin = ports.get(i);
                Port destination = ports.get(j);
                k++;
                outputCsv.setRouteId(k);
                outputCsv.setOrigin(origin.getPort());
                outputCsv.setDestination(destination.getPort());
                toReturn.add(outputCsv);
                k++;
                outputCsvInverse.setRouteId(k);
                outputCsvInverse.setOrigin(destination.getPort());
                outputCsvInverse.setDestination(origin.getPort());
                toReturn.add(outputCsvInverse);
                j++;
            }
            i++;
            j = i + 1;
        }
        return toReturn;
    }

    private static List<boolean[]> generateBooleanCombinations(int n) {
        return IntStream.range(0, (int) Math.pow(2, n))
                .mapToObj(i -> bitSetToArray(BitSet.valueOf(new long[]{i}), n))
                .toList();
    }

    private static boolean[] bitSetToArray(BitSet bs, int width) {
        boolean[] result = new boolean[width]; // all false
        bs.stream().forEach(i -> result[i] = true);
        return result;
    }
}
