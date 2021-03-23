package com.af.arabimotors;

import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.repositories.ConditionRepository;
import com.af.arabimotors.services.ConditionsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ArabimotorsApplicationTests {

	@Autowired
	private ConditionRepository conditionRepository;

	@Test
	void findAllConditions() {

	}

}
