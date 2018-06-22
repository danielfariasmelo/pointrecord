package br.com.liferay.daniel.pointrecord.exception.repository;

import br.com.liferay.daniel.pointrecord.domain.UnknownError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository interface PointExceptionRepository extends JpaRepository <UnknownError,Long> {

}
