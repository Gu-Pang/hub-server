package org.gupang.hub.domain.route;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PathType {

    JGRAPHT("JGraphT"),

    ;

    private final String strategy;
}
