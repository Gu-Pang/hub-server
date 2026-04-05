package org.gupang.hub.domain.route;

import org.gupang.hub.domain.vo.Coordinate;

public interface RouteDistanceCalculator {

    RouteEstimate calculate(Coordinate start, Coordinate end);

}
