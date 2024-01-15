package com.example.school.domain;

import com.example.school.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name="building_id")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    private String name;
    private String imageURL;
    private String purpose;
    private String item;
    private String time;
    private String caution;
    private String location;

}
