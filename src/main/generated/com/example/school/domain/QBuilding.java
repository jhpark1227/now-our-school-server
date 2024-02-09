package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuilding is a Querydsl query type for Building
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuilding extends EntityPathBase<Building> {

    private static final long serialVersionUID = -878146219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuilding building = new QBuilding("building");

    public final ListPath<BuildingHour, QBuildingHour> buildingHours = this.<BuildingHour, QBuildingHour>createList("buildingHours", BuildingHour.class, QBuildingHour.class, PathInits.DIRECT2);

    public final StringPath caution = createString("caution");

    public final ListPath<Facility, QFacility> facilities = this.<Facility, QFacility>createList("facilities", Facility.class, QFacility.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageURL = createString("imageURL");

    public final StringPath item = createString("item");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath location = createString("location");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public final StringPath purpose = createString("purpose");

    public final QSchool school;

    public QBuilding(String variable) {
        this(Building.class, forVariable(variable), INITS);
    }

    public QBuilding(Path<? extends Building> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuilding(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuilding(PathMetadata metadata, PathInits inits) {
        this(Building.class, metadata, inits);
    }

    public QBuilding(Class<? extends Building> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
    }

}

