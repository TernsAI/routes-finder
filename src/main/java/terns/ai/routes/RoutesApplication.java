package terns.ai.routes;

import eu.europa.ec.eurostat.jgiscotools.util.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.Geometry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import terns.ai.routes.csvMappings.NavalRoute;
import terns.ai.routes.csvMappings.Port;
import terns.ai.routes.csvMappings.RelNavalRouteCanals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class RoutesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RoutesApplication.class, args);
		//utils Objects
		CsvUtils csvUtils = new CsvUtils();
		SeaRouting sr = new SeaRouting();

		List<Port> ports = csvUtils.readPortsCsv();
		IterationUtils iterationUtils = new IterationUtils();
		List<NavalRoute> navalRoutes = iterationUtils.combineRoutes(ports);

		List<NavalRoute> navalRoutesAfterComputation = new ArrayList<>();
		List<boolean[]> booleans = IterationUtils.generateBooleanCombinations(12);
		AtomicInteger routesId= new AtomicInteger();
		routesId.set(1);
		List<RelNavalRouteCanals> relNavalRouteCanalsList = new ArrayList<>();
		HashSet geometryRoutes = new HashSet<>();
		navalRoutes.stream().forEach(navalRoute -> {
			booleans.stream().forEach(booleansRow ->{
				try {
					Port origin = ports.stream().filter(port -> port.getPort().equalsIgnoreCase(navalRoute.getOrigin())).findFirst().orElseThrow(Exception::new);
					Port destination = ports.stream().filter(port -> port.getPort().equalsIgnoreCase(navalRoute.getDestination())).findFirst().orElseThrow(Exception::new);
					Geometry routeGeom = sr.getRoute(origin.getLon(), origin.getLat(), destination.getLon(), destination.getLat(), booleansRow[0],booleansRow[1],booleansRow[2],booleansRow[3],booleansRow[4],booleansRow[5],booleansRow[6],booleansRow[7],booleansRow[8],booleansRow[9],booleansRow[10],booleansRow[11]).getGeometry();
					if(geometryRoutes.add(routeGeom)){
						NavalRoute navalRoute1 = new NavalRoute();
						navalRoute1.setId(routesId.get());
						navalRoute1.setDistance(GeoDistanceUtil.getLengthGeoKM(routeGeom));
						navalRoute1.setOrigin(navalRoute.getOrigin());
						navalRoute1.setDestination(navalRoute.getDestination());
						navalRoute1.setOrigin_port_id(navalRoute.getOrigin_port_id());
						navalRoute1.setDestination_port_id(navalRoute.getDestination_port_id());
						navalRoutesAfterComputation.add(navalRoute1);
						routesId.getAndIncrement();
						for (int i = 0; i < booleansRow.length; i++) {
							if(booleansRow[i]){
								RelNavalRouteCanals relNavalRouteCanals = new RelNavalRouteCanals(i+1, navalRoute.getId());
								relNavalRouteCanalsList.add(relNavalRouteCanals);
							}
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} );
		});
		csvUtils.writeRoutesCsv(navalRoutesAfterComputation);
		csvUtils.writeRelNavalRouteCanalsCsv(relNavalRouteCanalsList);


	}

}
