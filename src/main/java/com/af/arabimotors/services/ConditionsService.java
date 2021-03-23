package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.model.ConditionRequest;
import com.af.arabimotors.repositories.ConditionRepository;


@Service
public class ConditionsService {
	
	@Autowired
	ConditionRepository conditionRepository;
	
	public void saveNewCondition(ConditionRequest conditionRequest) {

		ConditionsEntity conditionsEntity = new ConditionsEntity();
		BeanUtils.copyProperties(conditionRequest, conditionsEntity);
		conditionsEntity.setConditionName(conditionRequest.getConditionName());
		conditionRepository.save(conditionsEntity);
	}
	
	public List<ConditionsEntity> getAllConditions(){
		List<ConditionsEntity> conditionsEntities = conditionRepository.findAllByEnabled(true);
		return conditionsEntities;
	}
	
	public void deleteCondition(String id) {
		conditionRepository.disableCondition(id);
		
	}
	
	public void updateCondition(ConditionsEntity conditionRequest) {
		conditionRepository.updateCondition(conditionRequest.getConditionName(), conditionRequest.getId());
	}
	
	public ConditionsEntity findConditionById(String id) {
		return conditionRepository.findById(id+"");
	}


}
