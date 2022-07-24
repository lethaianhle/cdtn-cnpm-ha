package app.pizzahut.security;

import app.pizzahut.response.UserModelRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Slf4j
public class MyUserDetails implements UserDetails {

    private UserModelRes userModelRes;

    public MyUserDetails(UserModelRes userModelRes) {
        this.userModelRes = userModelRes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userModelRes.getRoleName());
        log.info("-------------------" + userModelRes.getRoleName());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return userModelRes.getPassword();
    }

    @Override
    public String getUsername() {
        return userModelRes.getMobile();
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
