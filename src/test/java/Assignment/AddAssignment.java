package Assignment;

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

public class AddAssignment {
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
        for(Tema tema : service.getAllTeme()){
            int id = Integer.parseInt(tema.getID());
            if( id > biggestId){
                biggestId = id;
            }
        }
        return ((Integer)(biggestId + 1)).toString();
    }

    @Test
    public void addTema_addingValidTemaWithCorrectId_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Descriere",9,6);
        testService.addTema(validTema);

        Tema addedTema = null;
        for(Tema tema : testService.getAllTeme()){
            String id = tema.getID();
            if( id.equals(nextId)){
                addedTema = tema;
            }
        }

        assert addedTema != null;
    }

    @Test
    public void addTema_addingInvalidTemaWithIdNull_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(null,"Descriere",9,6);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }
    @Test
    public void addTema_addingInvalidTemaWithIdEmptyString_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema("","Descriere",9,6);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }
    @Test
    public void addTema_addingInvalidTemaWithDescriereEmptyString_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"",9,6);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    @Test
    public void addTema_addingValidTemaWithDescriptionTema1_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Tema1",9,6);
        testService.addTema(validTema);

        Tema addedTema = null;
        for(Tema tema : testService.getAllTeme()){
            String id = tema.getID();
            if( id.equals(nextId)){
                addedTema = tema;
            }
        }

        assert addedTema != null;
        assert addedTema.getDescriere().equals("Tema1");
    }

    @Test
    public void addTema_addingInvalidTemaWithDeadline0_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Description",0,6);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    @Test
    public void addTema_addingInvalidTemaWithDeadline15_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Description",15,6);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }


    @Test
    public void addTema_addingValidTemaWithDeadline7_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Tema1",7,6);
        testService.addTema(validTema);

        Tema addedTema = null;
        for(Tema tema : testService.getAllTeme()){
            String id = tema.getID();
            if( id.equals(nextId)){
                addedTema = tema;
            }
        }

        assert addedTema != null;
        assert addedTema.getDeadline() == 7;
    }

    @Test
    public void addTema_addingInvalidTemaWithPrimire0_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Description",7,0);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    @Test
    public void addTema_addingInvalidTemaWithPrimire15_shouldThrowValidationError () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Description",7,15);
        try{
            testService.addTema(validTema);
            assert false;
        } catch(ValidationException e){
        } catch(Exception e2) {
            assert false;
        }
    }

    @Test
    public void addTema_addingValidTemaWithPrimire7_shouldAdd () {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Tema1",7,7);
        testService.addTema(validTema);

        Tema addedTema = null;
        for(Tema tema : testService.getAllTeme()){
            String id = tema.getID();
            if( id.equals(nextId)){
                addedTema = tema;
            }
        }

        assert addedTema != null;
        assert addedTema.getPrimire() == 7;
    }

    @Test
    public void addTema_addingValidTemaTwoTimes_shouldAddOnlyOnce() {
        Service testService = createService();
        String nextId = getNextId(testService);
        Tema validTema = new Tema(nextId,"Tema1",7,7);
        testService.addTema(validTema);
        testService.addTema(validTema);

        int timesFound = 0;
        for(Tema tema : testService.getAllTeme()){
            String id = tema.getID();
            if( id.equals(nextId)){
                timesFound++;
            }
        }

        assert timesFound == 1;
    }

}
