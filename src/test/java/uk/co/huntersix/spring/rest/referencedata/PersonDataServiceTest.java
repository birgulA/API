package uk.co.huntersix.spring.rest.referencedata;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import uk.co.huntersix.spring.rest.exception.RestException;
import uk.co.huntersix.spring.rest.model.Person;

import static uk.co.huntersix.spring.rest.testutil.AssertJThrowableAssert.assertThrown;

/**
 * @author Birgul Ayaz
 */
@RunWith( SpringRunner.class )
public class PersonDataServiceTest
{
    @InjectMocks
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonByGivenNameAndSurname()
    {
        final Person person = personDataService.findPerson( "Smith", "Mary" );
        assertEquals( new Long( 1 ), person.getId() );
        assertEquals( "Mary", person.getFirstName() );
        assertEquals( "Smith", person.getLastName() );
    }

    @Test
    public void shouldThrowExceptionWhenThereIsNoMatchingPerson()
    {
        assertThrown( () -> personDataService.findPerson( "Smithxxx", "Mary" ) ) //
            .isInstanceOf( RestException.class ) //
            .hasMessageContaining( "Person not found" ); //
    }

    @Test
    public void shouldReturnPersonListByGivenSurname()
    {
        final List<Person> personList = personDataService.findPersonsBySurname( "Brown" );
        assertEquals( "Collin", personList.get( 0 ).getFirstName() );
        assertEquals( "Brown", personList.get( 0 ).getLastName() );
        assertEquals( "Birgul", personList.get( 1 ).getFirstName() );
        assertEquals( "Brown", personList.get( 1 ).getLastName() );
    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoMatchingPersonBySurname()
    {
        final List<Person> personList = personDataService.findPersonsBySurname( "Brownxxx" );
        assertEquals( Collections.emptyList(), personList );
    }

    @Test
    public void shouldReturnOneElementListWhenThereIsOnlyOneMatchingPersonBySurname()
    {
        final List<Person> personList = personDataService.findPersonsBySurname( "Archer" );
        assertEquals( "Brian", personList.get( 0 ).getFirstName() );
        assertEquals( "Archer", personList.get( 0 ).getLastName() );
    }

    @Test
    public void shouldAddPersonSuccessfully()
    {
        final Person createdPerson = personDataService.createPerson( "Chris", "Davies" );
        final Person addedPerson = personDataService.PERSON_DATA.stream()
            .filter( p -> p.getLastName().equalsIgnoreCase( "Davies" ) )
            .findFirst()
            .get();

        assertSame( createdPerson, addedPerson );
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenPersonAlreadyExist()
    {
        assertThrown( () -> personDataService.createPerson( "Mary", "Smith" ) ) //
            .isInstanceOf( RestException.class ) //
            .hasMessageContaining( "Person already exists" ); //
    }
}
