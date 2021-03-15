package com.siliconmtn.io.api.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

//Junit 5
import org.junit.jupiter.api.Test;

// Jackson
import com.fasterxml.jackson.core.JsonProcessingException;

// Spacelibs
import com.siliconmtn.io.api.ApiRequestException;
import com.siliconmtn.io.api.validation.factory.ParserFactory;
import com.siliconmtn.io.api.validation.factory.ParserIntfc;
import com.siliconmtn.io.api.validation.validator.ValidationDTO;
import com.siliconmtn.io.api.validation.validator.ValidatorIntfc.ValidatorType;

public class ValidateAopTest {

	/**
	 * Check to see if a succeeding set of validators does nothing
	 * @throws Throwable
	 */
	@Test
	@Validate
	public void testValidateAOPPassValidation() throws Throwable {
		getResults("testValidateAOPPassValidation", true, false, false);
	}
	
	/**
	 * Check to see if a failing set of validators returns a proper ApiResponse
	 * @throws Throwable
	 */
	@Test
	@Validate
	public void testValidateAOPFailValidation() throws Throwable {
		try {
		getResults("testValidateAOPPassValidation", false, false, false);
		} catch (ApiRequestException a) {
			System.out.println(a.getFailedValidations());
			assert(a.getFailedValidations().size() == 4);
		}
	}

	
	/**
	 * Ensure that providing nothing doesn't throw an exception
	 * @throws Throwable
	 */
	@Test
	public void testNoValidation() throws Throwable {
		getResults("testNoValidation", false, false, false);
	}
	

	/**
	 * Test if handling eceptions thrown from not being able to
	 * instantiate a parser occur correctly.
	 * @throws Throwable
	 */
	@Test
	@Validate
	public void testThrowException() throws Throwable {
		try {
			getResults("testThrowException", false, true, false);
		} catch (Throwable t) {
			assert(t.getMessage().equals("Data validation preperation failed."));
		}
	}
	
	
	/**
	 * Test the proper return when there is no parser for the supplied key
	 * @throws Throwable
	 */
	@Test
	@Validate
	public void testNoParser() throws Throwable {
		getResults("testNoParser", false, false, true);
	}
	
	
	/**
	 * Creates all needed items to test the validator with the name of the method being validated and
	 * whether the validation should pass or fail, returning the results of the request.
	 * @param methodName
	 * @param pass
	 * @return
	 * @throws Throwable
	 */
	@Test
	private void getResults(String methodName, boolean pass, boolean throwError, boolean nullParser) throws Throwable {

		ValidateAop validate = new ValidateAop();

        JoinPoint joinPoint = mock(JoinPoint.class);
        MethodSignature signature = mock(MethodSignature.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        ParserFactory pFact = mock(ParserFactory.class);
        ParserIntfc parser = mock(ParserIntfc.class);
        
        Method m = this.getClass().getMethod(methodName);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(m);
        if (nullParser) {
            when(pFact.parserDispatcher("com.siliconmtn.io.api.validation.ValidateAopTest." + methodName)).thenReturn(null);
        } else {
            when(pFact.parserDispatcher("com.siliconmtn.io.api.validation.ValidateAopTest." + methodName)).thenReturn(parser);
        }
        
        if (throwError) {
            when(parser.requestParser("Test")).thenThrow(new JsonProcessingException("Gonna fail here") {private static final long serialVersionUID = 1L;});
        } else {
            when(parser.requestParser("Test")).thenReturn(pass? buildPassingTestValidators():buildFailingTestValidators());
        }
        
        validate.request = request;
        validate.pFact = pFact;
        
        validate.beforeAdvice(joinPoint, "Test");
	}
	
	/**
	 * Build a list of validators that will all pass
	 * @return
	 */
	private List<ValidationDTO> buildPassingTestValidators() {
		List<ValidationDTO> fields = new ArrayList<>();

		fields.add(ValidationDTO.builder().type(ValidatorType.STRING).value("test").min("1").max("20").isRequired(true).build());
		fields.add(ValidationDTO.builder().type(ValidatorType.NUMBER).value("5").min("1").max("7").isRequired(true).build());
		
		return fields;
	}
	
	/**
	 * Build a list of validators that will fail Despite having succeeding validators amongst them.
	 * @return
	 */
	private List<ValidationDTO> buildFailingTestValidators() {
		List<ValidationDTO> fields = new ArrayList<>();


		fields.add(ValidationDTO.builder().type(ValidatorType.STRING).value("test").min("1").max("20").isRequired(true).build());
		fields.add(ValidationDTO.builder().type(ValidatorType.NUMBER).value("5").min("1").max("7").isRequired(true).build());
		fields.add(ValidationDTO.builder().type(ValidatorType.STRING).value("").min("1").max("20").isRequired(true).build());
		fields.add(ValidationDTO.builder().type(ValidatorType.NUMBER).value("").min("1").max("7").isRequired(true).build());
		
		return fields;
	}

}
