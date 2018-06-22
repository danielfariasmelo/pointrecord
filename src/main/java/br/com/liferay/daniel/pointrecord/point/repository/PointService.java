package br.com.liferay.daniel.pointrecord.point.repository;

import br.com.liferay.daniel.pointrecord.domain.Clockin;
import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.user.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service  public class PointService {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private UserService userService;

    /**
     * This method is responsible for register user point.
     * @param clockin
     * @return point object
     */
    public ResponseEntity<Point> register(Clockin clockin) {

        final User user = userService.findById(clockin.getPis());

        final Point point = new Point(clockin.getDateTime(),user);

        return new ResponseEntity<>(pointRepository.save(point),HttpStatus.OK);
    }
}
