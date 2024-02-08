package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuildingHour is a Querydsl query type for BuildingHour
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuildingHour extends EntityPathBase<BuildingHour> {

    private static final long serialVersionUID = 2137670873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuildingHour buildingHour = new QBuildingHour("buildingHour");

    public final QBuilding building;

    public final StringPath closingTime = createString("closingTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath openingTime = createString("openingTime");

    public QBuildingHour(String variable) {
        this(BuildingHour.class, forVariable(variable), INITS);
    }

    public QBuildingHour(Path<? extends BuildingHour> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuildingHour(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuildingHour(PathMetadata metadata, PathInits inits) {
        this(BuildingHour.class, metadata, inits);
    }

    public QBuildingHour(Class<? extends BuildingHour> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.building = inits.isInitialized("building") ? new QBuilding(forProperty("building"), inits.get("building")) : null;
    }

}

