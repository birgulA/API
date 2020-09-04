package uk.co.huntersix.spring.rest.referencedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import uk.co.huntersix.spring.rest.exception.RestException;
import uk.co.huntersix.spring.rest.model.Person;

@Service
public class PersonDataService
{
    public static List<Person> PERSON_DATA = Arrays.asList( //
        new Person( "Mary", "Smith" ), //
        new Person( "Brian", "Archer" ), //
        new Person( "Collin", "Brown" ),//
        new Person( "Birgul", "Brown" ) );

    public Person findPerson( String lastName, String firstName )
    {
        final List<Person> personList = getPeopleListByNameAndSurname( lastName, firstName );

        if ( CollectionUtils.isEmpty( personList ) )
        {
            throw new RestException( "Person not found", HttpStatus.NOT_FOUND );

        }
        return personList.get( 0 );

    }

    private List<Person> getPeopleListByNameAndSurname( final String lastName, final String firstName )
    {
        return PERSON_DATA.stream()
            .filter(
                p -> p.getFirstName().equalsIgnoreCase( firstName ) && p.getLastName().equalsIgnoreCase( lastName ) )
            .collect( Collectors.toList() );
    }

    public List<Person> findPersonsBySurname( String surname )
    {
        return PERSON_DATA.stream()
            .filter( p -> p.getLastName().equalsIgnoreCase( surname ) )
            .collect( Collectors.toList() );
    }

    public Person createPerson( final String firstName, final String lastName )
    {
        final List<Person> personList = getPeopleListByNameAndSurname( lastName, firstName );

        if ( !CollectionUtils.isEmpty( personList ) )
        {
            throw new RestException( "Person already exists", HttpStatus.BAD_REQUEST );
        }

        Person personToBeCreated = new Person( firstName, lastName );
        //work around solution, repository can be added to keep values qhen there is enough time.
        PERSON_DATA = new ArrayList<>( PERSON_DATA );
        PERSON_DATA.add( personToBeCreated );
        return personToBeCreated;
    }
}
