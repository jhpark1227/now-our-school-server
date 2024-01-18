package com.example.school.domain;

import com.example.school.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="facility_id")
    private Facility facility;
    private String year;
    private String month;
    private String day;

    private String start_time;
    private String end_time;
    private String duration;


    public void setMember(Member member){
        this.member = member;
        member.getReservationList().add(this);
    }
    public void setFacility(Facility facility){
        this.facility = facility;
        facility.getReservationList().add(this);
    }




}
