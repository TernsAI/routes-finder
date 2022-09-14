package terns.ai.routes;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import eu.europa.ec.eurostat.jgiscotools.feature.Feature;
import eu.europa.ec.eurostat.jgiscotools.util.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.Geometry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class RoutesApplication {

	public static void main(String[] args) throws IOException {
		IterationUtils iterationUtils = new IterationUtils();
		SpringApplication.run(RoutesApplication.class, args);
		SeaRouting sr = new SeaRouting();
		List<Port> ports = new CsvToBeanBuilder(new FileReader(new ClassPathResource(
				"coordinates.csv").getFile()))
				.withType(Port.class)
				.build()
				.parse();
		List<OutputCsv> output = iterationUtils.combineRoutes(ports);

		try (FileWriter writer = new FileWriter((new ClassPathResource(
				".").getFile()+"output.csv"))) {
			ColumnPositionMappingStrategy mappingStrategy =  new ColumnPositionMappingStrategy();
			mappingStrategy.setType(OutputCsv.class);
			String[] columns = { "Origin", "Destination" };
			mappingStrategy.setColumnMapping(columns);
			StatefulBeanToCsv beanWriter = new StatefulBeanToCsvBuilder(writer)
					.withMappingStrategy(mappingStrategy)
					.build();

			beanWriter.write(output);
		} catch (CsvRequiredFieldEmptyException e) {
			throw new RuntimeException(e);
		} catch (CsvDataTypeMismatchException e) {
			throw new RuntimeException(e);
		}
		Port santos = ports.stream().filter(port -> port.getPort().equalsIgnoreCase("santos")).findFirst().orElse(null);
		Port fredericia = ports.stream().filter(port -> port.getPort().equalsIgnoreCase("fredericia")).findFirst().orElse(null);

		Feature route = sr.getRoute(santos.getLon(), santos.getLat(), fredericia.getLon(), fredericia.getLat());
		Feature route2 = sr.getRoute(fredericia.getLon(), fredericia.getLat(), santos.getLon(), santos.getLat());
		Objects.equals(route.getGeometry(),route2.getGeometry());
		GeoDistanceUtil.getLengthGeoKM(route.getGeometry());
	}

}
