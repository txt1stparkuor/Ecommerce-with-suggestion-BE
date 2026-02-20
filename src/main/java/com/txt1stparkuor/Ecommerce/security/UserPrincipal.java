package com.txt1stparkuor.Ecommerce.security;

import com.txt1stparkuor.Ecommerce.domain.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

  private final String id;
  private final String username;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  private UserPrincipal(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

    return new UserPrincipal(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            authorities
    );
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
    return true;
  }
}
