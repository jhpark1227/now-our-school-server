package com.example.school.domain;

import com.example.school.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="school_id")
    private School school;

    private String userId;
    private String name;
    private String email;
    private String password;
    private String identify_num;
    private String phone;
    private String nickname;
    private Integer age;
    private Integer grade;
    private String imageURL;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보를 반환하는 로직을 구현
        return null;
    }

    @Override
    public String getPassword() {
        // 사용자의 암호를 반환하는 로직을 구현
        return this.password;
    }

    @Override
    public String getUsername() {
        // 사용자의 식별자(예: 이메일)를 반환하는 로직을 구현
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되었는지 여부를 반환하는 로직을 구현
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠겨있는지 여부를 반환하는 로직을 구현
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명(비밀번호)이 만료되었는지 여부를 반환하는 로직을 구현
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되어 있는지 여부를 반환하는 로직을 구현
        return true;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
