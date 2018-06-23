package br.com.liferay.daniel.pointrecord.point.repository;

import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository interface PointRepository extends JpaRepository <Point,Long> {

    @Query("SELECT COUNT (P) FROM Point P WHERE P.dateTime BETWEEN ?1 AND ?2 ")
    Integer roundTime (final LocalDateTime localDateTimeInitial, final LocalDateTime localDateTimeFinale);

    Boolean existsPointByUserAndDateTime (final User user, final LocalDateTime localDateTime);

    List<Point> findAllByUser(final User user);
}
