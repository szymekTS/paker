package pl.szymanski.paker.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szymanski.paker.models.User;
import pl.szymanski.paker.repository.UserRepo;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Autowired
	UserRepo user_R;

	@Override
	@Transactional
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = user_R.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + username));

		return UserDetails.build(user);
	}
}