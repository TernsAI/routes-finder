package terns.ai.routes;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;

public class CsvUtils {
    public void writeRoutesCsv(List<Port> ports) throws IOException {
        IterationUtils iterationUtils = new IterationUtils();
        List<NavalRoute> output = iterationUtils.combineRoutes(ports);
        try (FileWriter writer = new FileWriter("naval_route.csv")) {
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(NavalRoute.class);
            String[] columns = {"origin","destination","id","origin_port_id","destination_port_id"};
            mappingStrategy.setColumnMapping(columns);
            StatefulBeanToCsv beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(';')
                    .withApplyQuotesToAll(false)
                    .build();

            beanWriter.write(output);
        } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
        File mFile = new File("naval_route.csv");
        FileInputStream fis = new FileInputStream(mFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String result = "";
        String line = "";
        while( (line = br.readLine()) != null){
            result = result + "\n"+line;
        }

        result = "origin;destination;id;origin_port_id;destination_port_id" + result;

        mFile.delete();
        FileOutputStream fos = new FileOutputStream(mFile);
        fos.write(result.getBytes());
        fos.flush();
    }

    public List<Port> readPortsCsv() throws IOException {
        List<Port> ports = new CsvToBeanBuilder(new FileReader(new ClassPathResource(
                "coordinates.csv").getFile()))
                .withType(Port.class)
                .build()
                .parse();
        return ports;
    }

}
