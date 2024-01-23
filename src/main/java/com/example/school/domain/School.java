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
public class School extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL)
    private List<FAQ> faqList = new ArrayList<>();
    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL)
    private List<Theme> themes = new ArrayList<>();
    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL)
    private List<Member> memberList = new ArrayList<>();
    @OneToMany(mappedBy = "school",cascade = CascadeType.ALL)
    private List<Building> buildingList = new ArrayList<>();

}
