package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.entities.DeanEntity;
import pl.edu.agh.soa.model.Dean;

public class DeansMapper {

    public static Dean entityToModel(DeanEntity entity){
        Dean dean = new Dean();
        dean.setId(entity.getId());
        dean.setDegree(entity.getAcademic_degree());
        dean.setName(entity.getName());
        return dean;
    }

    public static DeanEntity modelToEntity(Dean dean){
        DeanEntity deanEntity = new DeanEntity();
        deanEntity.setAcademic_degree(dean.getDegree());
        deanEntity.setId(dean.getId());
        deanEntity.setName(dean.getName());
        return deanEntity;
    }

}
