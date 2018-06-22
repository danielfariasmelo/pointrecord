package br.com.liferay.daniel.pointrecord.point.repository;

import br.com.liferay.daniel.pointrecord.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository interface PointRepository extends JpaRepository <Point,Long> {

}
