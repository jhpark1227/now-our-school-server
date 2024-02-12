package com.example.school.announcement.repository;

import com.example.school.domain.Announcement;
import com.example.school.domain.School;
import com.example.school.domain.enums.AnnouncementType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.school.domain.QAnnouncement.announcement;

@RequiredArgsConstructor
public class AnnouncementRepositoryImpl implements AnnouncementRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Announcement> findByType(School school, AnnouncementType type, Pageable pageable) {
        List<Announcement> list = queryFactory
                .selectFrom(announcement)
                .where(
                        typeEq(type),
                        schoolEq(school)
                )
                .orderBy(announcement.createdAt.desc())
                .fetch();

        long totalCount = queryFactory
                .select(announcement.count())
                .from(announcement)
                .where(
                        typeEq(type),
                        schoolEq(school)
                )
                .fetchOne();

        return new PageImpl<>(list, pageable, totalCount);
    }

    BooleanExpression typeEq(AnnouncementType type){
        return type!=null ? announcement.type.eq(type) : null;
    }

    BooleanExpression schoolEq(School school){
        return school!=null ? announcement.school.eq(school) : null;
    }
}
