package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTheme is a Querydsl query type for Theme
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTheme extends EntityPathBase<Theme> {

    private static final long serialVersionUID = 1789060808L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTheme theme = new QTheme("theme");

    public final com.example.school.domain.common.QBaseEntity _super = new com.example.school.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<Facility, QFacility> facilities = this.<Facility, QFacility>createList("facilities", Facility.class, QFacility.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QSchool school;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTheme(String variable) {
        this(Theme.class, forVariable(variable), INITS);
    }

    public QTheme(Path<? extends Theme> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTheme(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTheme(PathMetadata metadata, PathInits inits) {
        this(Theme.class, metadata, inits);
    }

    public QTheme(Class<? extends Theme> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
    }

}

