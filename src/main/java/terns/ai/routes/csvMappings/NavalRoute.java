package terns.ai.routes.csvMappings;

import com.opencsv.bean.CsvBindByName;

public class NavalRoute {
    private String origin;
    private String destination;
    private int id;
    private double distance;
    private int origin_port_id;
    private int destination_port_id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getOrigin_port_id() {
        return origin_port_id;
    }

    public void setOrigin_port_id(int origin_port_id) {
        this.origin_port_id = origin_port_id;
    }

    public int getDestination_port_id() {
        return destination_port_id;
    }

    public void setDestination_port_id(int destination_port_id) {
        this.destination_port_id = destination_port_id;
    }

    @Override
    public String toString() {
        return "NavalRoute{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                ", id=" + id +
                ", origin_port_id=" + origin_port_id +
                ", destination_port_id=" + destination_port_id +
                '}';
    }
}
