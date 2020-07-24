package uk.co.huntersix.spring.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.model.PersonRequest;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

/**
 * An entry point of Person Controller.
 */

@RestController
@RequestMapping(value = "/api/v1")
@Api( tags = "person")
public class PersonController
{
    private PersonDataService personDataService;

    public PersonController( @Autowired PersonDataService personDataService )
    {
        this.personDataService = personDataService;
    }

    /**
     * Gets a person matching with first name and last name.
     *
     * @param lastName  last name of person
     * @param firstName first name of person
     * @return person a matching person with givens
     */
    @GetMapping( "/person/{lastName}/{firstName}" )
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation( value = "Obtains a person with given first name and last name" )
    @ApiResponses( value = {//
        @ApiResponse( code = 500, message = "Unexpected error" ), //
        @ApiResponse( code = 400, message = "Bad Request" ) } )
    public ResponseEntity<Person> person( @PathVariable( value = "lastName" ) String lastName,
        @PathVariable( value = "firstName" ) String firstName )
    {
        final Person person = personDataService.findPerson( lastName, firstName );
        return ResponseEntity.ok( person );
    }

    /**
     * Retrieves a list of all people with a particular surname
     *
     * @param surname surname.
     * @return List of persons.
     */
    @ApiOperation( value = "Gets person list by given surname" )
    @ApiResponses( value = { @ApiResponse( code = 200, message = "Returns person list" ),
        @ApiResponse( code = 500, message = "Unexpected error" ) } )
    @RequestMapping( value = "/persons/{surname}", method = RequestMethod.GET )
    public ResponseEntity<List<Person>> getPersons( @PathVariable( value = "surname" ) String surname )
    {
        final List<Person> personList = personDataService.findPersonsBySurname( surname );
        return ResponseEntity.ok( personList );
    }

    /**
     * Creates a person if doesn't exist.
     *
     * @param personRequest person request
     * @return created person
     */
    @PostMapping( "/person/create" )
    @ApiOperation( value = "Creates a person" )
    @ResponseStatus( value = HttpStatus.CREATED )
    @ApiResponses( value = {//
        @ApiResponse( code = 500, message = "Unexpected error" ), //
        @ApiResponse( code = 400, message = "Bad Request" ) } )
    public ResponseEntity<Person> createPerson( @ApiParam( "Person" ) @Valid @RequestBody PersonRequest personRequest )
    {
        Person person = personDataService.createPerson( personRequest.getFirstName(), personRequest.getLastName() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( person );
    }
}
