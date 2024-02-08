package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFacility is a Querydsl query type for Facility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFacility extends EntityPathBase<Facility> {

    private static final long serialVersionUID = 1053616452L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFacility facility = new QFacility("facility");

    public final com.example.school.domain.common.QBaseEntity _super = new com.example.school.domain.common.QBaseEntity(this);

    public final QBuilding building;

    public final StringPath caution = createString("caution");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageURL = createString("imageURL");

    public final StringPath item = createString("item");

    public final EnumPath<com.example.school.domain.enums.FacilityKeyword> keyword = createEnum("keyword", com.example.school.domain.enums.FacilityKeyword.class);

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final StringPath purpose = createString("purpose");

    public final ListPath<Reservation, QReservation> reservationList = this.<Reservation, QReservation>createList("reservationList", Reservation.class, QReservation.class, PathInits.DIRECT2);

    public final ListPath<Review, QReview> reviewList = this.<Review, QReview>createList("reviewList", Review.class, QReview.class, PathInits.DIRECT2);

    public final QSchool school;

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final EnumPath<com.example.school.domain.enums.FacilityTag> tag = createEnum("tag", com.example.school.domain.enums.FacilityTag.class);

    public final QTheme theme;

    public final StringPath time = createString("time");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QFacility(String variable) {
        this(Facility.class, forVariable(variable), INITS);
    }

    public QFacility(Path<? extends Facility> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFacility(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFacility(PathMetadata metadata, PathInits inits) {
        this(Facility.class, metadata, inits);
    }

    public QFacility(Class<? extends Facility> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.building = inits.isInitialized("building") ? new QBuilding(forProperty("building"), inits.get("building")) : null;
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
        this.theme = inits.isInitialized("theme") ? new QTheme(forProperty("theme"), inits.get("theme")) : null;
    }

}

