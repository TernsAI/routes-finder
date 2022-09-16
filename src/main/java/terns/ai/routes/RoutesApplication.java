package terns.ai.routes;

import eu.europa.ec.eurostat.jgiscotools.util.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		List<Port> ports = CsvUtils.readPortsCsv();
		IterationUtils.combineCanalsAndRoutes(ports);
	}

}
