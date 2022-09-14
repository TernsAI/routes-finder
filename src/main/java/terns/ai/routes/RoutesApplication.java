package terns.ai.routes;

import com.opencsv.bean.CsvToBeanBuilder;
import eu.europa.ec.eurostat.jgiscotools.feature.Feature;
import eu.europa.ec.eurostat.jgiscotools.util.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.Geometry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class RoutesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RoutesApplication.class, args);
		SeaRouting sr = new SeaRouting();
		List<Port> ports = new CsvToBeanBuilder(new FileReader(new ClassPathResource(
				"coordinates.csv").getFile()))
				.withType(Port.class)
				.build()
				.parse();
		ports.forEach(System.out::println);
		Port santos = ports.stream().filter(port -> port.getPort().equalsIgnoreCase("santos")).findFirst().orElse(null);
		Port fredericia = ports.stream().filter(port -> port.getPort().equalsIgnoreCase("fredericia")).findFirst().orElse(null);

		Feature route = sr.getRoute(santos.getLon(), santos.getLat(), fredericia.getLon(), fredericia.getLat());
		Feature route2 = sr.getRoute(fredericia.getLon(), fredericia.getLat(), santos.getLon(), santos.getLat());
		Objects.equals(route.getGeometry(),route2.getGeometry());
		GeoDistanceUtil.getLengthGeoKM(route.getGeometry());
	}

}
