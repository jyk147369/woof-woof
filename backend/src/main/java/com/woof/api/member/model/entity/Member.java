package com.woof.api.member.model.entity;

import com.woof.api.bookmark.model.Bookmark;
import com.woof.api.common.BaseEntity;
import com.woof.api.orders.model.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Member extends BaseEntity implements UserDetails {

    private String memberEmail;
    private String memberPw;
    private String memberName;
    private String memberNickname;
    private String authority;
    private Boolean status;
    private String profileImage;

    @OneToMany(mappedBy = "member")
    List<Bookmark> carts = new ArrayList<>();

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
