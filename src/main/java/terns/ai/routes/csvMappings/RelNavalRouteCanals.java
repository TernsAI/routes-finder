package terns.ai.routes.csvMappings;

public class RelNavalRouteCanals {
    private int canals_id;
    private int naval_route_id;

    public int getCanals_id() {
        return canals_id;
    }

    public void setCanals_id(int canals_id) {
        this.canals_id = canals_id;
    }

    public int getNaval_route_id() {
        return naval_route_id;
    }

    public void setNaval_route_id(int naval_route_id) {
        this.naval_route_id = naval_route_id;
    }

    @Override
    public String toString() {
        return "RelNavalRouteCanals{" +
                "canals_id=" + canals_id +
                ", naval_route_id=" + naval_route_id +
                '}';
    }

    public RelNavalRouteCanals(int canals_id, int naval_route_id) {
        this.canals_id = canals_id;
        this.naval_route_id = naval_route_id;
    }
}
