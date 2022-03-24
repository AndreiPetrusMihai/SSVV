package Student;

import domain.Student;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.Objects;

public class AddStudent {

    Service createService(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();


        String filenameStudent = "fisiere/StudentiTest.xml";
        String filenameTema = "fisiere/TemeTest.xml";
        String filenameNota = "fisiere/NoteTest.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        return service;
    }

    public String getNextId(Service service){
        Integer biggestId = 0;
        for(Student student : service.getAllStudenti()){
            int id = Integer.parseInt(student.getID());
            if( id > biggestId){
                biggestId = id;
            }
        }
        return ((Integer)(biggestId + 1)).toString();
    }

    @Test
    public void addStudent_addingValidStudentWithGroupMinus1_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",-1,"andrei@gmail.com");
        try{
            testService.addStudent(validStudent);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    @Test
    public void addStudent_addingValidStudentWithGroup0_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",0,"andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert addedStudent.getGrupa() == 0;
    }

    @Test
    public void addStudent_addingValidStudentWithGroup1_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",1,"andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert addedStudent.getGrupa() == 1;
    }

    @Test
    public void addStudent_addingValidStudentWithGroupMaxIntMinusOne_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",Integer.MAX_VALUE-1,"andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert addedStudent.getGrupa() == Integer.MAX_VALUE-1;
    }

    @Test
    public void addStudent_addingValidStudentWithGroupMaxInt_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",Integer.MAX_VALUE,"andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert addedStudent.getGrupa() == Integer.MAX_VALUE;
    }

    @Test
    public void addStudent_addingValidStudentWithNonEmptyStringId_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",123,"andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
    }

    @Test
    public void addStudent_addingValidStudentWithIdNull_shouldThrowValidationError () {
        Service testService = createService();
        Student validStudent = new Student(null,"Andrei",-1,"andrei@gmail.com");
        try{
            testService.addStudent(validStudent);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    //This is test case number 9 from Req_EC_BVA_all_TC
    @Test
    public void addStudent_addingValidStudentWithIdEmptyString_shouldThrowValidationError () {
        Service testService = createService();
        Student validStudent = new Student("","Andrei",-1,"andrei@gmail.com");
        try{
            testService.addStudent(validStudent);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    @Test
    public void addStudent_addingValidStudentWithNameNonEmptyString_shouldAdd(){
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",10,"andrei@gmail.com");

        try {
            testService.addStudent(validStudent);
        }catch (Exception e){
            assert false;
        }
        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert Objects.equals(addedStudent.getNume(), "Andrei");
    }

    @Test
    public void addStudent_addingInvalidStudentWithNullName_shouldThrowValidationException(){
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,null,10,"andrei@gmail.com");

        try {
            testService.addStudent(validStudent);
            assert false;
        }catch (ValidationException ignored){
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    public void addStudent_addingInvalidStudentWithNameEmptyString_shouldThrowValidationException(){
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"",10,"andrei@gmail.com");

        try {
            testService.addStudent(validStudent);
            assert false;
        }catch (ValidationException ignored){
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    public void addStudent_addingValidStudentWithEmailNonEmptyString_shouldAdd(){
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",11,"andrei@gmail.com");

        try {
            testService.addStudent(validStudent);
        }catch (Exception e){
            assert false;
        }
        Student addedStudent = null;
        for(Student student : testService.getAllStudenti()){
            String id = student.getID();
            if( id.equals(nextId)){
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert Objects.equals(addedStudent.getEmail(), "andrei@gmail.com");
    }

    @Test
    public void addStudent_addingInvalidStudentWithEmailNull_shouldThrowValidationException(){
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"Andrei",10,null);

        try {
            testService.addStudent(validStudent);
            assert false;
        }catch (ValidationException ignored){
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    public void addStudent_addingInvalidStudentWithEmailEmptyString_shouldThrowValidationException(){
        Service testService = createService();
        String nextId = getNextId(testService);
        Student validStudent = new Student(nextId,"",10,"");

        try {
            testService.addStudent(validStudent);
            assert false;
        }catch (ValidationException ignored){
        }catch (Exception e){
            assert false;
        }
    }
}
