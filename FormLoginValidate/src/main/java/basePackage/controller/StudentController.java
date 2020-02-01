package basePackage.controller;

import basePackage.model.Student;
import basePackage.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/")
    public ModelAndView home (Pageable pageable){

        return new ModelAndView("home","students",studentService.findAll(pageable));
    }

    @GetMapping("/edit-student/{id}")
    public ModelAndView edit(@PathVariable(name = "id")Long id){
        return new ModelAndView("student/edit","student",studentService.findById(id));
    }

    @PostMapping("/edit-student")
    public ModelAndView updateStudent(@ModelAttribute(name = "student") Student student, Pageable pageable){
        studentService.save(student);
        return new ModelAndView("home","students",studentService.findAll(pageable));
    }
    @GetMapping("/create-student")
    public ModelAndView create(){
        return new ModelAndView("student/create","student",new Student());
    }

    @PostMapping("/create-student")
    public ModelAndView addStudent(@Validated @ModelAttribute(name = "student")Student student,BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasFieldErrors()){
            studentService.save(student);
            modelAndView.setViewName("home");

            return modelAndView;
        }else {
            modelAndView.setViewName("student/create");
            modelAndView.addObject("student",student);
            return modelAndView;
        }

    }

    @GetMapping("/delete-student/{id}")
    public ModelAndView delete(@PathVariable(name = "id")Long id){
        return new ModelAndView("student/delete","student",studentService.findById(id));
    }

    @PostMapping("/delete-student")
    public ModelAndView deleteStudent(@ModelAttribute(name = "id")Long id,Pageable pageable){
        studentService.delete(id);
        return new ModelAndView("home","students",studentService.findAll(pageable));
    }

}
