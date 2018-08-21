package uk.co.networkrail.trackgeometry.repository;

import uk.co.networkrail.trackgeometry.domain.CaptainsLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CaptainsLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaptainsLogRepository extends JpaRepository<CaptainsLog, Long> {

}
