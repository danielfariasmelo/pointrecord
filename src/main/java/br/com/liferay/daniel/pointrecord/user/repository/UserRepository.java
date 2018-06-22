package br.com.liferay.daniel.pointrecord.user.repository;

import br.com.liferay.daniel.pointrecord.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository interface UserRepository extends JpaRepository <User,String> {
}
