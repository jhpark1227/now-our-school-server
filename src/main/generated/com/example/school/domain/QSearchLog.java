package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSearchLog is a Querydsl query type for SearchLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchLog extends EntityPathBase<SearchLog> {

    private static final long serialVersionUID = 1725816315L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSearchLog searchLog = new QSearchLog("searchLog");

    public final NumberPath<Long> count = createNumber("count", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSchool school;

    public final StringPath value = createString("value");

    public QSearchLog(String variable) {
        this(SearchLog.class, forVariable(variable), INITS);
    }

    public QSearchLog(Path<? extends SearchLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSearchLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSearchLog(PathMetadata metadata, PathInits inits) {
        this(SearchLog.class, metadata, inits);
    }

    public QSearchLog(Class<? extends SearchLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
    }

}

