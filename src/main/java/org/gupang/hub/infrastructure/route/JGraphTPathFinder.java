package org.gupang.hub.infrastructure.route;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gupang.common.exception.CustomException;
import org.gupang.hub.application.route.dto.HubRouteInfo;
import org.gupang.hub.domain.entity.HubRoute;
import org.gupang.hub.domain.repository.HubRouteRepository;
import org.gupang.hub.domain.route.PathFinder;
import org.gupang.hub.domain.route.PathType;
import org.gupang.hub.global.exception.HubErrorCode;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JGraphTPathFinder implements PathFinder {

    private final HubRouteRepository hubRouteRepository;

    @Override
    public List<HubRouteInfo> findPath(UUID startHubId, UUID endHubId) {
        List<HubRoute> allRoutes = hubRouteRepository.findAllWithHubs();

        Graph<UUID, HubRouteEdge> graph = buildGraph(allRoutes);
        GraphPath<UUID, HubRouteEdge> path = calculateShortestPath(graph, startHubId, endHubId);

        return convertToHubRouteInfos(path);
    }

    @Override
    public boolean supports(PathType pathType) {
        return pathType == PathType.JGRAPHT;
    }

    private Graph<UUID, HubRouteEdge> buildGraph(List<HubRoute> routes) {
        Graph<UUID, HubRouteEdge> graph = new SimpleDirectedWeightedGraph<>(HubRouteEdge.class);
        routes.forEach(route -> addRouteToGraph(graph, route));
        return graph;
    }

    private void addRouteToGraph(Graph<UUID, HubRouteEdge> graph, HubRoute route) {
        UUID source = route.getStartHub().getHubId();
        UUID target = route.getEndHub().getHubId();

        graph.addVertex(source);
        graph.addVertex(target);

        HubRouteEdge edge = new HubRouteEdge(route);
        graph.addEdge(source, target, edge);
        graph.setEdgeWeight(edge, route.getDuration());
    }

    private GraphPath<UUID, HubRouteEdge> calculateShortestPath(Graph<UUID, HubRouteEdge> graph, UUID start, UUID end) {
        GraphPath<UUID, HubRouteEdge> path = new DijkstraShortestPath<>(graph).getPath(start, end);
        if (path == null) {
            throw new CustomException(HubErrorCode.HUB_ROUTE_NOT_FOUND);
        }
        return path;
    }

    private List<HubRouteInfo> convertToHubRouteInfos(GraphPath<UUID, HubRouteEdge> path) {
        return path.getEdgeList().stream()
                .map(edge -> HubRouteInfo.from(edge.getHubRoute()))
                .toList();
    }

    @Getter
    @AllArgsConstructor
    private static class HubRouteEdge extends DefaultWeightedEdge {

        private final HubRoute hubRoute;

    }
}
