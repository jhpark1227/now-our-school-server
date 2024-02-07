package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSearchRank is a Querydsl query type for SearchRank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchRank extends EntityPathBase<SearchRank> {

    private static final long serialVersionUID = 1960863829L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSearchRank searchRank = new QSearchRank("searchRank");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> ranking = createNumber("ranking", Integer.class);

    public final QSchool school;

    public final StringPath value = createString("value");

    public QSearchRank(String variable) {
        this(SearchRank.class, forVariable(variable), INITS);
    }

    public QSearchRank(Path<? extends SearchRank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSearchRank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSearchRank(PathMetadata metadata, PathInits inits) {
        this(SearchRank.class, metadata, inits);
    }

    public QSearchRank(Class<? extends SearchRank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
    }

}

