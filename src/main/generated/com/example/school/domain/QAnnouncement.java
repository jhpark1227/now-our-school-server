package com.example.school.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnouncement is a Querydsl query type for Announcement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnouncement extends EntityPathBase<Announcement> {

    private static final long serialVersionUID = -1017737368L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnnouncement announcement = new QAnnouncement("announcement");

    public final com.example.school.domain.common.QBaseEntity _super = new com.example.school.domain.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSchool school;

    public final StringPath title = createString("title");

    public final EnumPath<com.example.school.domain.enums.AnnouncementType> type = createEnum("type", com.example.school.domain.enums.AnnouncementType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QAnnouncement(String variable) {
        this(Announcement.class, forVariable(variable), INITS);
    }

    public QAnnouncement(Path<? extends Announcement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnnouncement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnnouncement(PathMetadata metadata, PathInits inits) {
        this(Announcement.class, metadata, inits);
    }

    public QAnnouncement(Class<? extends Announcement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new QSchool(forProperty("school")) : null;
    }

}

