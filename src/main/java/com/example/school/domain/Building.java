package com.example.school.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Builder
public class Building {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private double latitude;

    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;
    @OneToMany(mappedBy = "building",cascade = CascadeType.ALL)
    private List<Building> buildingList = new ArrayList<>();
}
