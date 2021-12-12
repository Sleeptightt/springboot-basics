package com.example.demo;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.example.demo.front.businessdelegate.implementation.BusinessDelegateImpl;
import com.example.demo.front.businessdelegate.interfaces.BusinessDelegate;
import com.example.demo.front.model.person.Businessentity;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
@Log4j2
public class BusinessDelegateTest {
	
	private final static String URL = "http://localhost:8080/api";

	private final static String BEN_URL = URL + "/ben/";
	private final static String STPROV_URL = URL + "/stprov/";
	private final static String ADDR_URL = URL + "/addr/";
    private final static String PER_URL = URL + "/person/";
    private final static String PHONE_URL = URL + "/benphone/";
    private final static String PHONE_TYPE_URL = URL + "/phonetype/";
    private final static String ADDR_TYPE_URL = URL + "/addrtype/";
    
    @Mock
    private static RestTemplate restTemplate;

    @InjectMocks
    private static BusinessDelegate bd;
    
    @BeforeAll
    public static void setUp() {
        bd = new BusinessDelegateImpl();
        bd.setRestTemplate(restTemplate);
        log.info("-----> TASK TESTS STARTED <-----");
    }
    
 // Businessentity --------------------------------------------------------------------------------------------
    
    @Test
    public void findAllBusinessentitys_test(){
        Businessentity[] list = new Businessentity[5];


        for (int i = 0; i < list.length; i++) {
            Businessentity bs = new Businessentity();
            list[i] = bs;
        }


        when(restTemplate.getForObject(BEN_URL, Businessentity[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllBusinessentitys().size(), 5,
                "Unexpected size");
    }
    
    
    @Test
    public void findBusinessentitysById_test(){
        Businessentity bs = new Businessentity();

        when(restTemplate.getForObject(BEN_URL+bs.getBusinessentityid(), Businessentity.class)
        		).thenReturn(bs);


        assertEquals(bd.findBusinessentityById(bs.getBusinessentityid()).getBusinessentityid(), bs.getBusinessentityid(),
                "Unexpected id");
    }
    
    @Test
    public void deleteBusinessentity_test(){
    	Businessentity ben = new Businessentity();

        bd.deleteBusinessentity(ben);

        verify(restTemplate).delete(BEN_URL+ben.getBusinessentityid());
    }
    
    @Test
    public void saveBusinessentity_test(){
    	Businessentity ben = new Businessentity();
        HttpEntity<Businessentity> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(BEN_URL, request, Businessentity.class))
                .thenReturn(ben);
        
        assertEquals(bd.saveBusinessentity(ben).getBusinessentityid(), ben.getBusinessentityid(), "Unexpected id");
    }
    
    @Test
    public void editBusinessentity_test(){
    	Businessentity ben = new Businessentity();

        bd.editBusinessentity(ben);

        verify(restTemplate).put(BEN_URL, ben, Businessentity.class);
    }
    
 // Person --------------------------------------------------------------------------------------------
	
    
}
