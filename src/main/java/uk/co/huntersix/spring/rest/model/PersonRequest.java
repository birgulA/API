package uk.co.huntersix.spring.rest.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Birgul Ayaz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest
{

    @ApiModelProperty( position = 0 )
    @NotNull( message = "Please provide first name" )
    private String firstName;

    @ApiModelProperty( position = 1 )
    @NotNull( message = "Please provide last name" )
    private String lastName;
}
