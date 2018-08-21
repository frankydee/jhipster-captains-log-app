package uk.co.networkrail.trackgeometry.repository;

import uk.co.networkrail.trackgeometry.domain.ELR;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ELR entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ELRRepository extends JpaRepository<ELR, Long> {

}
