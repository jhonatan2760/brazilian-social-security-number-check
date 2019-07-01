package com.jhonatansouza.bssnc.repository;

import com.jhonatansouza.bssnc.model.AccessResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<AccessResponse, Long> {


}
