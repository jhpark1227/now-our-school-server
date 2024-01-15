package com.example.school.domain;

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
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "facility",cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();
    private String name;
    private String imageURL;
    private String purpose;
    private String item;
    private String time;
    private String caution;
    private String location;

}
