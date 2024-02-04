package com.example.school.entity;

import com.example.school.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Inquiry extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        private Member member;

        private String title;

        private String body;

    public void setMember(Member member){
        if(this.member != null)
            member.getInquiryList().remove(this);
        this.member = member;
        member.getInquiryList().add(this);
    }
}

