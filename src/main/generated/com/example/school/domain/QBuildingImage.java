package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuildingImage is a Querydsl query type for BuildingImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuildingImage extends EntityPathBase<BuildingImage> {

    private static final long serialVersionUID = 1844132102L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuildingImage buildingImage = new QBuildingImage("buildingImage");

    public final QBuilding building;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageURL = createString("imageURL");

    public QBuildingImage(String variable) {
        this(BuildingImage.class, forVariable(variable), INITS);
    }

    public QBuildingImage(Path<? extends BuildingImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuildingImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuildingImage(PathMetadata metadata, PathInits inits) {
        this(BuildingImage.class, metadata, inits);
    }

    public QBuildingImage(Class<? extends BuildingImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.building = inits.isInitialized("building") ? new QBuilding(forProperty("building"), inits.get("building")) : null;
    }

}

