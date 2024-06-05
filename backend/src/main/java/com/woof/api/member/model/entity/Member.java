package com.woof.api.member.model.entity;

import com.woof.api.cart.model.Cart;
import com.woof.api.orders.model.Orders;
import lombok.*;
import org.joda.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    private String memberEmail;
    private String memberPw;
    private String memberName;
    private String memberNickname;
    private String authority;
    private boolean status;
    private String profileImage;
    private LocalDateTime updatedAt;
    private

    @OneToMany(mappedBy = "member")
    List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Orders> orders = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public String getUsername() {
        return memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

    @Override
    public String getPassword(){
        return memberPw;
    }
}
