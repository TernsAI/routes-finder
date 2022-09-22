package terns.ai.routes;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.ClassPathResource;
import terns.ai.routes.csvMappings.NavalRoute;
import terns.ai.routes.csvMappings.Port;
import terns.ai.routes.csvMappings.RelNavalRouteCanals;

import java.io.*;
import java.util.List;

public class CsvUtils {
    public static void writeRoutesCsv(List<NavalRoute> navalRoutes) throws IOException {
        try (FileWriter writer = new FileWriter("naval_route.csv")) {
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(NavalRoute.class);
            String[] columns = {"origin","destination","id","distance","origin_port_id","destination_port_id"};
            mappingStrategy.setColumnMapping(columns);
            StatefulBeanToCsv beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(';')
                    .withApplyQuotesToAll(false)
                    .build();

            beanWriter.write(navalRoutes);
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

        result = "origin;destination;id;distance;origin_port_id;destination_port_id" + result;

        mFile.delete();
        FileOutputStream fos = new FileOutputStream(mFile);
        fos.write(result.getBytes());
        fos.flush();
    }

    public static void writeRelNavalRouteCanalsCsv(List<RelNavalRouteCanals> relNavalRouteCanalsList) throws IOException {

        try (FileWriter writer = new FileWriter("rel_naval_route__canals.csv")) {
            ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
            mappingStrategy.setType(RelNavalRouteCanals.class);
            String[] columns = {"canals_id","naval_route_id"};
            mappingStrategy.setColumnMapping(columns);
            StatefulBeanToCsv beanWriter = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(';')
                    .withApplyQuotesToAll(false)
                    .build();

            beanWriter.write(relNavalRouteCanalsList);
        } catch (CsvRequiredFieldEmptyException | IOException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
        File mFile = new File("rel_naval_route__canals.csv");
        FileInputStream fis = new FileInputStream(mFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String result = "";
        String line = "";
        while( (line = br.readLine()) != null){
            result = result + "\n"+line;
        }

        result = "canals_id;naval_route_id" + result;

        mFile.delete();
        FileOutputStream fos = new FileOutputStream(mFile);
        fos.write(result.getBytes());
        fos.flush();
    }


    public static List<Port> readPortsCsv() throws IOException {
        List<Port> ports = new CsvToBeanBuilder(new FileReader("/home/ec2-user/coordinates.csv"))
                .withType(Port.class)
                .build()
                .parse();
        return ports;
    }

}
