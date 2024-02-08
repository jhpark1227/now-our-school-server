package com.example.school.domain;

import com.example.school.domain.common.BaseEntity;
import com.example.school.domain.enums.FacilityKeyword;
import com.example.school.domain.enums.FacilityTag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Facility extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "facility",cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "facility",cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="building_id")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="theme_id")
    private Theme theme;

    private String name;
    private String extraName;
    private String imageURL;
    private String purpose;
    private String item;
    private String time;
    private String caution;
    private String location;
    private Float score;
    private Boolean isTheme;

    private String description;

    @Enumerated(EnumType.STRING)
    private FacilityTag tag;

    @Enumerated(EnumType.STRING)
    private FacilityKeyword keyword;
}
