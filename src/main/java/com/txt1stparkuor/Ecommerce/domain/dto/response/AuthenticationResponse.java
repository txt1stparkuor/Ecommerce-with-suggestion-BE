package com.txt1stparkuor.Ecommerce.domain.dto.response;

import com.txt1stparkuor.Ecommerce.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    String id;
    Collection<? extends GrantedAuthority> authorities;

}
