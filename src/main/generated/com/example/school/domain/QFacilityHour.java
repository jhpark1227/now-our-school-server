package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFacilityHour is a Querydsl query type for FacilityHour
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFacilityHour extends EntityPathBase<FacilityHour> {

    private static final long serialVersionUID = -804187832L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFacilityHour facilityHour = new QFacilityHour("facilityHour");

    public final StringPath closingTime = createString("closingTime");

    public final QFacility facility;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath openingTime = createString("openingTime");

    public QFacilityHour(String variable) {
        this(FacilityHour.class, forVariable(variable), INITS);
    }

    public QFacilityHour(Path<? extends FacilityHour> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFacilityHour(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFacilityHour(PathMetadata metadata, PathInits inits) {
        this(FacilityHour.class, metadata, inits);
    }

    public QFacilityHour(Class<? extends FacilityHour> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.facility = inits.isInitialized("facility") ? new QFacility(forProperty("facility"), inits.get("facility")) : null;
    }

}

