package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.entities.FacultyEntity;
import pl.edu.agh.soa.entities.StudentEntity;
import pl.edu.agh.soa.mappers.FacultiesMapper;
import pl.edu.agh.soa.mappers.StudentsMapper;
import pl.edu.agh.soa.model.Faculty;
import pl.edu.agh.soa.model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class FacultyDAO {

    @PersistenceContext(unitName = "students")
    EntityManager em;

    public List<Faculty> getAllFaculties() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<FacultyEntity> criteriaQuery = builder.createQuery(FacultyEntity.class);
        Root<FacultyEntity> root = criteriaQuery.from(FacultyEntity.class);
        criteriaQuery.select(root);
        TypedQuery<FacultyEntity> query = em.createQuery(criteriaQuery);
        List<FacultyEntity> resultList = query.getResultList();
        if(resultList == null)
            return null;
        return resultList.stream().map(FacultiesMapper::entityToModel).collect(Collectors.toList());
    }




}
