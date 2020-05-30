package pl.edu.agh.soa.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FacultyDAO {

    @PersistenceContext(unitName = "students")
    EntityManager em;




}
