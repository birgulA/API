package uk.co.huntersix.spring.rest.controller;

import java.util.Arrays;

import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.huntersix.spring.rest.exception.RestException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.model.PersonRequest;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

@RunWith( SpringRunner.class )
@WebMvcTest( PersonController.class )
public class PersonControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception
    {
        when( personDataService.findPerson( any(), any() ) ).thenReturn( new Person( "Mary", "Smith" ) );
        this.mockMvc.perform( get( "/api/v1/person/smith/mary" ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "id" ).exists() )
            .andExpect( jsonPath( "firstName" ).value( "Mary" ) )
            .andExpect( jsonPath( "lastName" ).value( "Smith" ) );
    }

    @Test
    public void shouldReturnNoContentWhenPersonCanNotFoundByNameAndSurname() throws Exception
    {
        when( personDataService.findPerson( any(), any() ) ).thenThrow(
            new RestException( "Person not found", HttpStatus.NO_CONTENT ) );
        mockMvc.perform( MockMvcRequestBuilders.get( "/api/v1/person/ayaz/birgul" ) )
            .andExpect( status().isNoContent() )
            .andExpect( content().string( StringContains.containsString( "Person not found" ) ) )
            .andReturn();
    }

    @Test
    public void shouldReturnPersonListWhenThereIsMatchingPersonBySurname() throws Exception
    {
        when( personDataService.findPersonsBySurname( "smith" ) ).thenReturn(
            Arrays.asList( new Person( "Mary", "Smith" ) ) );
        this.mockMvc.perform( get( "/api/v1/persons/smith" ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( jsonPath( "$", hasSize( 1 ) ) )
            .andExpect( jsonPath( "$[0].firstName" ).value( "Mary" ) )
            .andExpect( jsonPath( "$[0].lastName" ).value( "Smith" ) );
    }

    @Test
    public void shouldCreatePersonWhenInputIsValid() throws Exception
    {
        when( personDataService.createPerson( any(), any() ) ).thenReturn( new Person( "Luke", "Wolf" ) );
        PersonRequest personRequest = new PersonRequest( "Luke", "Wolf" );

        mockMvc.perform( MockMvcRequestBuilders.post( "/api/v1/person/create" )
            .contentType( "application/json" )
            .content( objectMapper.writeValueAsString( personRequest ) ) )
            .andExpect( status().isCreated() )
            .andExpect( MockMvcResultMatchers.jsonPath( "$.firstName" ).value( "Luke" ) )
            .andExpect( MockMvcResultMatchers.jsonPath( "$.lastName" ).value( "Wolf" ) )
            .andReturn();
    }

    @Test
    public void shouldReturnBadRequestWhenPersonExists() throws Exception
    {
        when( personDataService.createPerson( any(), any() ) ).thenThrow(
            new RestException( "Person already exists", HttpStatus.BAD_REQUEST ) );

        PersonRequest personRequest = new PersonRequest( "Luke", "Wolf" );

        mockMvc.perform( MockMvcRequestBuilders.post( "/api/v1/person/create" )
            .contentType( "application/json" )
            .content( objectMapper.writeValueAsString( personRequest ) ) )
            .andExpect( status().isBadRequest() )
            .andExpect( content().string( StringContains.containsString( "Person already exists" ) ) )
            .andReturn();

    }
}
