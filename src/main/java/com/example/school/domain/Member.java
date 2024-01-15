package com.example.school.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    private String name;
    private String email;
    private String password;
    private String identify_num;
    private String phone;
    private Integer age;
    private Integer grade;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
