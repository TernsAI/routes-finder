package terns.ai.routes.csvMappings;

import com.opencsv.bean.CsvBindByName;

public class Port {
    @CsvBindByName
    private int portid;
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

    public int getPortid() {
        return portid;
    }

    public void setPortid(int portid) {
        this.portid = portid;
    }

    @Override
    public String toString() {
        return "Port{" +
                "portid=" + portid +
                ", port='" + port + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
