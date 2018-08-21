package uk.co.networkrail.trackgeometry.repository;

import uk.co.networkrail.trackgeometry.domain.Track;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Track entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

}
