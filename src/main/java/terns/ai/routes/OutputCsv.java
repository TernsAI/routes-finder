package terns.ai.routes;

import com.opencsv.bean.CsvBindByName;

public class OutputCsv {
    @CsvBindByName
    private String origin;
    @CsvBindByName
    private String destination;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "OutputCsv{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
