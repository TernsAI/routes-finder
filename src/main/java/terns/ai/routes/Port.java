package terns.ai.routes;

import com.opencsv.bean.CsvBindByName;

public class Port {
    @CsvBindByName
    private String port;
    @CsvBindByName
    private double lat;
    @CsvBindByName
    private double lon;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Port{" +
                "port='" + port + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
