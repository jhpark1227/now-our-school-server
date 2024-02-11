package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchool is a Querydsl query type for School
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchool extends EntityPathBase<School> {

    private static final long serialVersionUID = -406844843L;

    public static final QSchool school = new QSchool("school");

    public final com.example.school.domain.common.QBaseEntity _super = new com.example.school.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSchool(String variable) {
        super(School.class, forVariable(variable));
    }

    public QSchool(Path<? extends School> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchool(PathMetadata metadata) {
        super(School.class, metadata);
    }

}

