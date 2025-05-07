package com.spinner.www.study.controller;

import com.spinner.www.study.service.StudyFacadeService;
import com.spinner.www.study.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudyController.class)
public class StudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudyFacadeService studyFacadeService;
    @MockBean
    private StudyService studyService;

    
}
