package com.ASM;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ASM.dao.AccountDAO;
import com.ASM.dao.UserDAO;
import com.ASM.model.Account;
import com.ASM.model.User;
import com.ASM.service.AccountService;
import com.ASM.service.SessionService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	AccountService accountService;
	@Autowired
	AccountDAO accDao;
	@Autowired
	UserDAO userDao;

	@Autowired
	SessionService session;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/profile/**","/booking/**").authenticated()
						.requestMatchers("/admin/**").hasAnyRole("ADMIN")
						.anyRequest().permitAll())
				
				.formLogin(formLogin -> formLogin.loginPage("/login")
						.usernameParameter("email")
						.passwordParameter("password")
						.loginProcessingUrl("/security/login")
						.defaultSuccessUrl("/login/success", false)
						.failureUrl("/security/login/error"))
				
				/* .rememberMe(rememberme -> rememberme.tokenValiditySeconds(86400)) */

				.exceptionHandling(denied -> denied.accessDeniedPage("/security/unauthoried"))
				
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/security/logoff/success"));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		return new CustomUserDetailsService(accountService);
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(authenticationProvider);
	}
	
	
	
	
	class CustomUserDetailsService implements UserDetailsService {
	    private final AccountService accountService;

	    public CustomUserDetailsService(AccountService accountService) {
	        this.accountService = accountService;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        try {
	            User user = accountService.findById(email);
	            Account acc = accDao.findByEmailJPQL(email);
				session.set("user", user);
				session.set("useracc", acc);
	            return org.springframework.security.core.userdetails.User.builder()
	                    .username(email)
	                    .password(passwordEncoder().encode(user.getAccount().getPassword()))
	                    .roles(user.getAccount().getRole())
	                    .build();
	        } catch (NoSuchElementException e) {
	            throw new UsernameNotFoundException("User not found: " + email);
	        }
	    }
	}	
}