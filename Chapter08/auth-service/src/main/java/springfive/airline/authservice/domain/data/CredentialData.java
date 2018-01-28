package springfive.airline.authservice.domain.data;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springfive.airline.authservice.domain.Scope;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialData implements UserDetails {

  String email;

  String password;

  Set<Scope> scopes;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.scopes.stream().map(scope -> new SimpleGrantedAuthority(scope.getValue())).collect(
        Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
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
