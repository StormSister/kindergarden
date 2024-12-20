package com.monika.monischool.controller;

import com.monika.monischool.model.Person;
import com.monika.monischool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @Value("${monischool.pageSize}")
    private int defaultPageSize;

    @Value("${monischool.contact.successMsg}")
    private String message;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if (null != person.getMoniClass() && null != person.getMoniClass().getName()) {
            model.addAttribute("enrolledClass", person.getMoniClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        return "dashboard.html";
    }
}