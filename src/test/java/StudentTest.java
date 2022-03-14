import domain.Student;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentTest {

    Service createService(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();


        String filenameStudent = "fisiere/StudentiTest.xml";
        String filenameTema = "fisiere/TemeTest.xml";
        String filenameNota = "fisiere/NoteTest.xml";

        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        return service;
    }

    @Test
    public void addStudent_addingValidStudentWithGroup936_shouldAdd () {
        Service testService = createService();


        Integer biggestId = 0;
        for(Student student : testService.getAllStudenti()){
            int id = Integer.parseInt(student.getID());
            if( id > biggestId){
                biggestId = id;
            }
        }
        int nextId = biggestId + 1;
        Student validStudent = new Student(((Integer)nextId).toString(),"Andrei",936,"andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            int id = Integer.parseInt(student.getID());
            if( id == nextId){
                addedStudent = student;
            }
        }
        if (addedStudent == null){
            assert false;
        }
        assert addedStudent.getNume() == "Andrei";
        assert addedStudent.getGrupa() == 936;
    }

    @Test
    public void addStudent_addingValidStudentWithNegativeGroup_shouldThrowError () {
        Service testService = createService();
        Student validStudent = new Student("100","Andrei",-5,"andrei@gmail.com");
        try{
            testService.addStudent(validStudent);
            assert false;
        } catch(Exception e){
        }
    }

}
