package terns.ai.routes;

import terns.ai.routes.csvMappings.NavalRoute;
import terns.ai.routes.csvMappings.Port;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class IterationUtils {

    public List<NavalRoute> combineRoutes(List<Port> ports) {
        List<NavalRoute> toReturn = new ArrayList<>();
        int i = 0;
        int j = 1;
        int k = 0;
        while (i < ports.size() - 1) {
            while (j < ports.size()) {
                NavalRoute navalRoute = new NavalRoute();
                NavalRoute navalRouteInverse = new NavalRoute();
                Port origin = ports.get(i);
                Port destination = ports.get(j);
                k++;
                navalRoute.setId(k);
                navalRoute.setOrigin_port_id(origin.getPortid());
                navalRoute.setDestination_port_id(destination.getPortid());
                navalRoute.setOrigin(origin.getPort());
                navalRoute.setDestination(destination.getPort());
                toReturn.add(navalRoute);
                k++;
                navalRouteInverse.setId(k);
                navalRouteInverse.setOrigin_port_id(destination.getPortid());
                navalRouteInverse.setDestination_port_id(origin.getPortid());
                navalRouteInverse.setOrigin(destination.getPort());
                navalRouteInverse.setDestination(origin.getPort());
                toReturn.add(navalRouteInverse);
                j++;
            }
            i++;
            j = i + 1;
        }
        return toReturn;
    }

    public static List<boolean[]> generateBooleanCombinations(int n) {
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
