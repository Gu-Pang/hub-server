-- 1. 기존 테이블 삭제
DROP TABLE IF EXISTS p_hub_route CASCADE;
DROP TABLE IF EXISTS p_hub CASCADE;

-- 2. 허브(Hub) 테이블 생성
CREATE TABLE p_hub (
    hub_id UUID PRIMARY KEY,
    hub_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    address_detail VARCHAR(255),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID NOT NULL,
    updated_at TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP,
    deleted_by UUID,

    CONSTRAINT uk_hub_name UNIQUE (hub_name),
    CONSTRAINT chk_korea_latitude CHECK (latitude >= 33.0 AND latitude <= 38.9),
    CONSTRAINT chk_korea_longitude CHECK (longitude >= 124.5 AND longitude <= 132.0)
);

-- 3. 허브 간 경로(HubRoute) 테이블 생성
CREATE TABLE p_hub_route (
    route_id UUID PRIMARY KEY,
    start_hub_id UUID NOT NULL,
    end_hub_id UUID NOT NULL,
    duration INT NOT NULL,
    distance DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP,
    deleted_by UUID,

    CONSTRAINT fk_route_start_hub FOREIGN KEY (start_hub_id) REFERENCES p_hub(hub_id),
    CONSTRAINT fk_route_end_hub FOREIGN KEY (end_hub_id) REFERENCES p_hub(hub_id),
    CONSTRAINT uk_hub_route UNIQUE (start_hub_id, end_hub_id),
    CONSTRAINT chk_route_not_same CHECK (start_hub_id != end_hub_id),
    CONSTRAINT chk_route_positive CHECK (duration > 0 AND distance > 0)
);
