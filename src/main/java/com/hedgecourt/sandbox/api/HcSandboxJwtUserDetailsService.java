package com.hedgecourt.sandbox.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HcSandboxJwtUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(HcSandboxJwtUserDetailsService.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (log.isDebugEnabled()) log.debug("HC Jwt User Details Service: {}", username);

    return User.withUsername(username)
        .password("")
        .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
        .build();
  }
}
