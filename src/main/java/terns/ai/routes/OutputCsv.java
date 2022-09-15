package terns.ai.routes;

import com.opencsv.bean.CsvBindByName;

public class OutputCsv {
    @CsvBindByName
    private String origin;
    @CsvBindByName
    private String destination;

    @CsvBindByName
    private int routeId;

    @CsvBindByName
    private int canal;

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

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    @Override
    public String toString() {
        return "OutputCsv{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", routeId=" + routeId +
                ", canal=" + canal +
                '}';
    }
}
