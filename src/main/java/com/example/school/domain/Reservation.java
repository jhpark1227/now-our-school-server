package com.example.school.domain;

import com.example.school.domain.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="facility_id")
    private Facility facility;

    @OneToMany(mappedBy = "reservation")
    List<Image> images = new ArrayList<>();
    private String year;
    private String month;
    private String day;

    private Integer start_time;
    private Integer end_time;
    private Integer duration;
    private Boolean back;

    public void setMember(Member member){
        this.member = member;
        member.getReservationList().add(this);
    }
    public void setFacility(Facility facility){
        this.facility = facility;
        facility.getReservationList().add(this);
    }




}
