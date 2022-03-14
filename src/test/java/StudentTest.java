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

    void cleanFiles(){
        String filenameStudent = "fisiere/emptyStudent.xml";
        String filenameTema = "fisiere/emptyTema.xml";
        String filenameNota = "fisiere/emptyNota.xml";
        File fileStudent = new File(filenameStudent);
        File fileTema = new File(filenameTema);
        File fileNota = new File(filenameNota);
        fileStudent.delete();
        fileTema.delete();
        fileNota.delete();
    }

    Service createService(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();


        String filenameStudent = "fisiere/emptyStudent.xml";
        String filenameTema = "fisiere/emptyTema.xml";
        String filenameNota = "fisiere/emptyNota.xml";
        try{
            new File(filenameStudent).createNewFile();
            new File(filenameTema).createNewFile();
            new File(filenameNota).createNewFile();
        }catch (IOException e){

        }

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
        Student validStudent = new Student("100","Andrei",936,"andrei@gmail.com");
        testService.addStudent(validStudent);
        List<Student> students = new ArrayList();
        testService.getAllStudenti().forEach(students::add);
        Student addedStudent = students.get(0);
        assert addedStudent.getNume() == "Andrei";
        assert addedStudent.getGrupa() == 936;
        cleanFiles();
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
        cleanFiles();
    }

}
