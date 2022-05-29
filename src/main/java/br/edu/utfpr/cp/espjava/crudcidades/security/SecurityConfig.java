package br.edu.utfpr.cp.espjava.crudcidades.security;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("john").password(cifrador().encode("teste123")).authorities("listar")
//				.and().withUser("anna").password(cifrador().encode("teste123")).authorities("admin");
//	}

	@Bean
	public PasswordEncoder cifrador() {
		return new BCryptPasswordEncoder();
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/").hasAnyAuthority("listar", "admin")
					.antMatchers("/criar").hasAuthority("admin")
					.antMatchers("/excluir").hasAuthority("admin")
					.antMatchers("/preparaAlterar").hasAuthority("admin")
					.antMatchers("/alterar").hasAuthority("admin")
					.antMatchers("/mostrar").authenticated()
			        .antMatchers("/h2-console/**").permitAll()
					.anyRequest().denyAll()
					.and()
					.formLogin()
					.loginPage("/login.html").permitAll()
					.defaultSuccessUrl("/", false)
					.and()
					.logout().permitAll();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void printSenhas() {
		System.out.println(this.cifrador().encode("teste123"));
	}
}
