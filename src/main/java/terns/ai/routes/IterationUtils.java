package terns.ai.routes;

import eu.europa.ec.eurostat.jgiscotools.util.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terns.ai.routes.csvMappings.NavalRoute;
import terns.ai.routes.csvMappings.Port;
import terns.ai.routes.csvMappings.RelNavalRouteCanals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class IterationUtils {

    public static List<NavalRoute> combineRoutes(List<Port> ports) {
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
                j++;
            }
            i++;
            j = i + 1;
        }
        return toReturn;
    }

    public static CombinationOutput combineCanalsAndRoutes(NavalRoute navalRoute, List<Port> ports) {
        Logger logger = LoggerFactory.getLogger(IterationUtils.class);
        SeaRouting sr = new SeaRouting();
        List<NavalRoute> navalRoutesAfterComputation = new ArrayList<>();
        List<RelNavalRouteCanals> relNavalRouteCanalsList = new ArrayList<>();
        List<boolean[]> booleans = generateBooleanCombinations(12);
        HashSet geometryRoutes = new HashSet<>();
        for (boolean[] booleansRow : booleans) {
            try {
                logger.debug("provo la combinazione \n" + booleansRow.toString());
                Port origin = ports.stream().filter(port -> port.getPort().equalsIgnoreCase(navalRoute.getOrigin())).findFirst().orElseThrow(Exception::new);
                Port destination = ports.stream().filter(port -> port.getPort().equalsIgnoreCase(navalRoute.getDestination())).findFirst().orElseThrow(Exception::new);
                Geometry routeGeom = sr.getRoute(origin.getLon(), origin.getLat(), destination.getLon(), destination.getLat(), booleansRow[0], booleansRow[1], booleansRow[2], booleansRow[3], booleansRow[4], booleansRow[5], booleansRow[6], booleansRow[7], booleansRow[8], booleansRow[9], booleansRow[10], booleansRow[11]).getGeometry();
                if (geometryRoutes.add(routeGeom)) {
                    NavalRoute navalRoute1 = new NavalRoute();
                    NavalRoute navalRouteInverse = new NavalRoute();
                    navalRoute1.setId(Math.abs(routeGeom.hashCode()) + ThreadLocalRandom.current().nextInt(3, 9995 + 1));
                    navalRoute1.setDistance(GeoDistanceUtil.getLengthGeoKM(routeGeom)/1.852);
                    navalRoute1.setOrigin(navalRoute.getOrigin());
                    navalRoute1.setDestination(navalRoute.getDestination());
                    navalRoute1.setOrigin_port_id(navalRoute.getOrigin_port_id());
                    navalRoute1.setDestination_port_id(navalRoute.getDestination_port_id());
                    navalRoutesAfterComputation.add(navalRoute1);
                    logger.info("ho aggiunto la rotta dopo computazione: \n" + navalRoute1);
                    navalRouteInverse.setId(navalRoute1.getId() + ThreadLocalRandom.current().nextInt(9996, 19754 + 1) + 1);
                    navalRouteInverse.setDistance(navalRoute1.getDistance());
                    navalRouteInverse.setOrigin(navalRoute.getDestination());
                    navalRouteInverse.setDestination(navalRoute.getOrigin());
                    navalRouteInverse.setOrigin_port_id(navalRoute.getDestination_port_id());
                    navalRouteInverse.setDestination_port_id(navalRoute.getOrigin_port_id());
                    navalRoutesAfterComputation.add(navalRouteInverse);
                    logger.debug("ho aggiunto la rotta inversa: \n" + navalRouteInverse);
                    for (int i = 0; i < booleansRow.length; i++) {
                        if (booleansRow[i]) {
                            RelNavalRouteCanals relNavalRouteCanals = new RelNavalRouteCanals(i + 1, navalRoute1.getId());
                            relNavalRouteCanalsList.add(relNavalRouteCanals);
                            logger.info("ho aggiunto la relazione: \n" + relNavalRouteCanals);
                            RelNavalRouteCanals relNavalRouteCanalsInverse = new RelNavalRouteCanals(i + 1, navalRouteInverse.getId());
                            relNavalRouteCanalsList.add(relNavalRouteCanalsInverse);
                            logger.debug("ho aggiunto la relazione inversa: \n" + relNavalRouteCanalsInverse);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new CombinationOutput(navalRoutesAfterComputation, relNavalRouteCanalsList);
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
