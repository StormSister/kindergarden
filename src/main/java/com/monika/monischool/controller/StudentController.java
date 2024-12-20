package com.monika.monischool.controller;

import com.monika.monischool.model.Person;
import com.monika.monischool.repository.CoursesRepository;
import com.monika.monischool.repository.MoniClassRepository;
import com.monika.monischool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping("/student")
public class StudentController {

    MoniClassRepository moniClassRepository;

    PersonRepository personRepository;

    CoursesRepository coursesRepository;

    public StudentController(MoniClassRepository moniClassRepository, PersonRepository personRepository,
                             CoursesRepository coursesRepository) {
        this.moniClassRepository = moniClassRepository;
        this.personRepository = personRepository;
        this.coursesRepository = coursesRepository;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return modelAndView;

    }


}
