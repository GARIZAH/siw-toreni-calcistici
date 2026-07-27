package it.uniroma3.siw.authentication;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	public static final String ADMIN_ROLE = "ADMIN";
	private final DataSource dataSource;
	public SecurityConfiguration(DataSource dataSource) {
		this.dataSource= dataSource;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
	    JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
	    manager.setUsersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	    manager.setAuthoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?");
	    return manager;
	  }
	@Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	
@Bean	
protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/rest/**"));
	    httpSecurity.authorizeHttpRequests(authorize -> {
	    	authorize.requestMatchers("/rest/**").permitAll();
	    	  authorize.requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/css/**", "/images/**", "/favicon.ico","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll();
	    	
	    	
	    	  authorize.requestMatchers("/css/**", "/js/**", "/images/**").permitAll();
	    	  authorize.requestMatchers("/", "/tornei", "/tornei/**", "/squadre/**").permitAll();
	    	  authorize.requestMatchers(HttpMethod.POST, "/register", "/login").permitAll();
	    	  authorize.requestMatchers("/registrato/**").hasAnyAuthority("USER", "ADMIN");
	    	  authorize.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE);
	    	  authorize.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE);   
	    	  authorize.anyRequest().authenticated();
	    	  });

	    httpSecurity.formLogin(form -> {
	    	  form.loginPage("/login").permitAll();
	    	  form.defaultSuccessUrl("/success", true);
	    	  form.failureUrl("/login?error=true");
	    	  });

	    httpSecurity.logout(logout -> {
	    	  logout.logoutUrl("/logout");
	    	  logout.logoutSuccessUrl("/");
	    	  logout.invalidateHttpSession(true);
	    	  logout.deleteCookies("JSESSIONID");
	    	  logout.clearAuthentication(true);
	    	  logout.permitAll();
	    	});
	    return httpSecurity.build();

	  }

}
