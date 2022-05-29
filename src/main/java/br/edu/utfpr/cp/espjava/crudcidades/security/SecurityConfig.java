package br.edu.utfpr.cp.espjava.crudcidades.security;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/").hasAnyAuthority("listar", "admin")
				.antMatchers("/criar").hasAnyAuthority("admin")
				.antMatchers("/excluir").hasAnyAuthority("admin")
				.antMatchers("/prepararAlterar").hasAnyAuthority("admin")
				.antMatchers("/alterar").hasAnyAuthority("admin")
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/mostrar").authenticated()
				.anyRequest().authenticated()
				.and()
				.headers().frameOptions().sameOrigin()
				.and()
				.formLogin().loginPage("/login.html").permitAll()
				.and()
				.logout().permitAll();
	}

	@Bean
	public PasswordEncoder cifrador() {
		return new BCryptPasswordEncoder();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void printSenhas() {
		System.out.println(this.cifrador().encode("123"));
	}

	@EventListener(InteractiveAuthenticationSuccessEvent.class)
	public void printUsuarioAtual(InteractiveAuthenticationSuccessEvent event) {
		var usuario = event.getAuthentication().getName();
		System.out.println(usuario);
	}
}
