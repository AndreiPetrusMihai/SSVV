package IntegrationTesting;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.time.LocalDate;
import java.util.Date;

public class Integration {

    Service createService() {
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

    public String getNextStudentId(Service service) {
        Integer biggestId = 0;
        for (Student student : service.getAllStudenti()) {
            int id = Integer.parseInt(student.getID());
            if (id > biggestId) {
                biggestId = id;
            }
        }
        return ((Integer) (biggestId + 1)).toString();
    }

    public String getNextAssignmentId(Service service) {
        Integer biggestId = 0;
        for (Tema tema : service.getAllTeme()) {
            int id = Integer.parseInt(tema.getID());
            if (id > biggestId) {
                biggestId = id;
            }
        }
        return ((Integer) (biggestId + 1)).toString();
    }

    public String getNextGradeId(Service service) {
        Integer biggestId = 0;
        for (Nota nota : service.getAllNote()) {
            int id = Integer.parseInt(nota.getID());
            if (id > biggestId) {
                biggestId = id;
            }
        }
        return ((Integer) (biggestId + 1)).toString();
    }

    @Test
    public void addNota_addingInvalidNotaWithNonExistingStudent_shouldThrowValidationException() {
        Service service = createService();
        Nota invalidNota = new Nota("1", "1002400", getNextAssignmentId(service), 10, LocalDate.now());
        try {
            service.addNota(invalidNota, "nice");
        } catch (ValidationException ve) {
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void addStudent_addingValidStudentWithGroup0_shouldAdd() {
        Service testService = createService();
        String nextId = getNextStudentId(testService);
        Student validStudent = new Student(nextId, "Andrei", 0, "andrei@gmail.com");
        testService.addStudent(validStudent);

        Student addedStudent = null;
        for (Student student : testService.getAllStudenti()) {
            String id = student.getID();
            if (id.equals(nextId)) {
                addedStudent = student;
            }
        }

        assert addedStudent != null;
        assert addedStudent.getGrupa() == 0;
    }

    @Test
    public void addTema_addingValidTemaWithCorrectId_shouldAdd() {
        Service testService = createService();
        String nextId = getNextAssignmentId(testService);
        Tema validTema = new Tema(nextId, "Descriere", 9, 6);
        testService.addTema(validTema);

        Tema addedTema = null;
        for (Tema tema : testService.getAllTeme()) {
            String id = tema.getID();
            if (id.equals(nextId)) {
                addedTema = tema;
            }
        }

        assert addedTema != null;
    }

    @Test
    public void addStudentTemaNota_ValidData_shouldAddNota() {
        Service testService = createService();
        String nextStudentId = getNextStudentId(testService);
        String nextAssignmentId = getNextAssignmentId(testService);
        String nextGradeId = "2";

        Student validStudent = new Student(nextStudentId, "Andrei", 0, "andrei@gmail.com");
        testService.addStudent(validStudent);

        Tema validTema = new Tema(nextAssignmentId, "Descriere", 13, 12);
        testService.addTema(validTema);

        Tema addedTema = null;
        for (Tema tema : testService.getAllTeme()) {
            String id = tema.getID();
            if (id.equals(nextAssignmentId)) {
                addedTema = tema;
            }
        }

        assert addedTema != null;

        Nota validNota = new Nota(nextGradeId, nextStudentId, nextAssignmentId, 10, LocalDate.now());
        try {
            Double notaValue = testService.addNota(validNota, "nice");
            assert notaValue == null;
        } catch (Exception e) {
            assert false;
        }
    }
}
