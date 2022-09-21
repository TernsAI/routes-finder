package terns.ai.routes;

import terns.ai.routes.csvMappings.NavalRoute;
import terns.ai.routes.csvMappings.RelNavalRouteCanals;

import java.util.List;

public class CombinationOutput {
    private List<NavalRoute> routes;
    private List<RelNavalRouteCanals> canals;

    public CombinationOutput(List<NavalRoute> routes, List<RelNavalRouteCanals> canals) {
        this.routes = routes;
        this.canals = canals;
    }

    public List<NavalRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<NavalRoute> routes) {
        this.routes = routes;
    }

    public List<RelNavalRouteCanals> getCanals() {
        return canals;
    }

    public void setCanals(List<RelNavalRouteCanals> canals) {
        this.canals = canals;
    }
}
