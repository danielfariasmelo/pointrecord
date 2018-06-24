package br.com.liferay.daniel.pointrecord.point;

import br.com.liferay.daniel.pointrecord.domain.Clockin;
import br.com.liferay.daniel.pointrecord.domain.Point;
import br.com.liferay.daniel.pointrecord.domain.User;
import br.com.liferay.daniel.pointrecord.point.repository.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@RestController
@RequestMapping(value = "/api/clockin")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional (propagation=Propagation.REQUIRES_NEW, isolation = REPEATABLE_READ)
    public ResponseEntity<Point> register (@RequestBody Clockin clockin){
        return pointService.register(clockin);
    }

    @GetMapping(value = "/all/registers/users/{pis}")
    public List<Point> findAllRegistersByUser (@PathVariable String pis){
        return pointService.findAllByUser(pis);
    }



}
