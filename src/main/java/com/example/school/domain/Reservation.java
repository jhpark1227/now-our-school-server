package com.example.school.domain;

import com.example.school.domain.common.BaseEntity;
import com.example.school.domain.enums.AlertType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    private Integer users; //이용 인원
    private String year;
    private String month;
    private String day;

    private Integer start_time;
    private Integer end_time;
    private Integer duration;
    private Boolean back;
  //  @ElementCollection
   // @CollectionTable(name = "reservation_alerts", joinColumns = @JoinColumn(name = "reservation_id"))
    @Enumerated(EnumType.STRING)
    private Set<AlertType> alerts = new HashSet<>();
    public Set<AlertType> getAlerts() {
        return alerts != null ? alerts : new HashSet<>();
    }
    public void setAlerts(Set<AlertType> alerts) {
        this.alerts = alerts;
    }
    public void setMember(Member member){
        this.member = member;
        member.getReservationList().add(this);
    }
    public void setFacility(Facility facility){
        this.facility = facility;
        facility.getReservationList().add(this);
    }




}
