package com.bougsid.service;

import com.bougsid.grade.Grade;
import com.bougsid.grade.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ayoub on 6/30/2016.
 */
public interface ServiceRepository extends JpaRepository<Dept, Long> {

}
