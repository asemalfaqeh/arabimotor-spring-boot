package com.af.arabimotors.controllers.admin;



import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.model.request.ConditionRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.af.arabimotors.services.ConditionsService;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;


@RestController
public class ConditionController {

	@Autowired
	private ConditionsService conditionsService;
	
	@RequestMapping(value = WebUrlsConstants.ADMIN_CONDITIONS, method = RequestMethod.GET)
	public ModelAndView allConditions() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("conditions", conditionsService.getAllConditions());
		modelAndView.setViewName(WebViewsConstants.ADMIN_CONDITION_VIEW);
		return modelAndView;
	}
	
	// go to add new condition  
	@RequestMapping(value = WebUrlsConstants.ADMIN_ADD_CONDTITION, method = RequestMethod.GET)
	public ModelAndView addNewCondition(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebViewsConstants.ADMIN_ADD_NEW_CONDITION);
		return modelAndView;
	}

	// add new condition // 
	@RequestMapping(value = WebUrlsConstants.ADMIN_POST_NEW_CONDITION, method = RequestMethod.POST)
	public ModelAndView saveNewCondition(@Validated ConditionRequest conditionRequest, BindingResult bindingResult){

		ModelAndView modelAndView = new ModelAndView();

		if (bindingResult.hasErrors()){
			modelAndView.addObject("errorMessage", "Condition Not Saved");
			modelAndView.setViewName(WebViewsConstants.ADMIN_ADD_NEW_CONDITION);
		} else {
			conditionsService.saveNewCondition(conditionRequest);
			modelAndView.setViewName("redirect:" + WebUrlsConstants.ADMIN_CONDITIONS);
		}

		return modelAndView;

	}

	// find condition by id //
	@RequestMapping(value = WebUrlsConstants.ADMIN_EDIT_CONDITION+"/{id}", method = RequestMethod.GET)
	public ModelAndView editCondition(@PathVariable(name = "id") String id){
		System.out.printf("Edit Condition: " + id);
		ModelAndView modelAndView = new ModelAndView();
		ConditionsEntity conditionsEntity = conditionsService.findConditionById(id);
		modelAndView.addObject("condition", conditionsEntity);
		modelAndView.setViewName(WebViewsConstants.ADMIN_EDIT_CONDITION);
		System.out.println(" Conditiion: " + conditionsEntity.toString());
		return modelAndView;
	}
	
	//update condition by id//
	@RequestMapping(value = WebUrlsConstants.ADMIN_EDIT_CONDITION, method = RequestMethod.POST)
	public ModelAndView updateCondition(@Validated ConditionsEntity conditionRequest, BindingResult bindingResult){
		System.out.printf("Edit Condition: " + conditionRequest.toString());

		ModelAndView modelAndView = new ModelAndView();

		if (bindingResult.hasErrors()){
			modelAndView.addObject("errorMessage", "Condition Not Saved");
			modelAndView.setViewName(WebViewsConstants.ADMIN_EDIT_CONDITION);
		}else {
			conditionsService.updateCondition(conditionRequest);
			modelAndView.setViewName("redirect:" + WebUrlsConstants.ADMIN_CONDITIONS);
		}

		return modelAndView;
	}
	
	//delete condition by id //
	@RequestMapping(value = WebUrlsConstants.ADMIN_DELETE_CODNITION_BY_ID+"/{id}", method = RequestMethod.GET)
	public ModelAndView deleteCondition(@PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView();
        conditionsService.deleteCondition(id);
	    modelAndView.setViewName("redirect:" + WebUrlsConstants.ADMIN_CONDITIONS);
	    return modelAndView;
	}

}
