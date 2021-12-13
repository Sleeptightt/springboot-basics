package com.example.demo;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.front.model.person.Stateprovince;

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
	
    @Test
    public void findAllPersonsByPersonType_test(){
        Person[] list = new Person[5];
        String persontype = "type1";

        for (int i = 0; i < list.length; i++) {
            Person p = new Person();
            p.setPersontype(persontype);
            list[i] = p;
        }


        when( restTemplate.getForObject(PER_URL+
                "search/findAllByPersonType?persontype=" + persontype, Person[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersonsByPersonType(persontype).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllPersonsByTitle_test(){
        Person[] list = new Person[5];
        String title = "worker";

        for (int i = 0; i < list.length; i++) {
            Person p = new Person();
            p.setTitle(title);
            list[i] = p;
        }


        when( restTemplate.getForObject(PER_URL+
                "search/findAllByTitle?title=" + title, Person[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersonsByTitle(title).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllPersonsByBusinessentityid_test(){
        Person[] list = new Person[5];
        Integer businessentityid = 0;

        for (int i = 0; i < list.length; i++) {
            Person p = new Person();
            p.setBusinessentityid(businessentityid);
            list[i] = p;
        }


        when( restTemplate.getForObject(PER_URL+
                "search/findAllByBusinessEntity?businessentity=" + businessentityid, Person[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersonsByBusinessentityid(businessentityid).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllPersons_test(){
        Person[] list = new Person[5];

        for (int i = 0; i < list.length; i++) {
            Person p = new Person();
            list[i] = p;
        }


        when( restTemplate.getForObject(PER_URL, Person[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersons().size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findPersonById_test(){
        Person bs = new Person();
        
        when(restTemplate.getForObject(PER_URL+bs.getBusinessentityid(), Person.class)
        		).thenReturn(bs);


        assertEquals(bd.findPersonById(bs.getBusinessentityid()).getBusinessentityid(), bs.getBusinessentityid(),
                "Unexpected id");
    }
    
    @Test
    public void deletePerson_test(){
    	Person ben = new Person();

        bd.deletePerson(ben);

        verify(restTemplate).delete(PER_URL+ben.getBusinessentityid());
    }
    
    @Test
    public void savePerson_test(){
    	Person ben = new Person();
        HttpEntity<Person> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(PER_URL, request, Person.class))
                .thenReturn(ben);
        
        assertEquals(bd.savePerson(ben).getBusinessentityid(), ben.getBusinessentityid(), "Unexpected id");
    }
    
    @Test
    public void editPerson_test(){
    	Person ben = new Person();

        bd.editPerson(ben);

        restTemplate.put(PER_URL, ben, Person.class);
    }
    
//Stateprovince --------------------------------------------------------------------------------------------
	
    @Test
    public void findAllStateprovinces_test(){
        Stateprovince[] list = new Stateprovince[5];

        for (int i = 0; i < list.length; i++) {
            Stateprovince p = new Stateprovince();
            list[i] = p;
        }


        when( restTemplate.getForObject(STPROV_URL, Stateprovince[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllStateprovinces().size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findStateprovinceById_test(){
        Stateprovince bs = new Stateprovince();
        
        when(restTemplate.getForObject(STPROV_URL+bs.getStateprovinceid(), Stateprovince.class))
        		.thenReturn(bs);


        assertEquals(bd.findStateprovinceById(bs.getStateprovinceid()).getStateprovinceid(), bs.getStateprovinceid(),
                "Unexpected id");
    }
    
    @Test
    public void deleteStateprovince_test(){
    	Stateprovince ben = new Stateprovince();

        bd.deleteStateprovince(ben);

        verify(restTemplate).delete(STPROV_URL+ben.getStateprovinceid());
    }
    
    
    @Test
    public void saveStateprovince_test(){
    	Stateprovince ben = new Stateprovince();
        HttpEntity<Stateprovince> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(STPROV_URL, request, Stateprovince.class))
                .thenReturn(ben);
        
        assertEquals(bd.saveStateprovince(ben).getStateprovinceid(), ben.getStateprovinceid(), "Unexpected id");
    }
    
    @Test
    public void editStateprovince_test(){
    	Stateprovince ben = new Stateprovince();

        bd.editStateprovince(ben);

        restTemplate.put(STPROV_URL, ben, Stateprovince.class);
    }
    
  //Address --------------------------------------------------------------------------------------------
	
    @Test
    public void findAllAddressByModifiedDate_test(){
        Address[] list = new Address[5];
        Timestamp modifieddate = new Timestamp(System.currentTimeMillis());

        for (int i = 0; i < list.length; i++) {
            Address p = new Address();
            p.setModifieddate(modifieddate);
            list[i] = p;
        }

        String format = "yyyy-MM-dd HH:mm:ss.SSS";
		DateFormat formatter = new SimpleDateFormat(format);
        when( restTemplate.getForObject(ADDR_URL+
                "search/findAllByModifieddate?modifieddate=" + formatter.format(modifieddate), Address[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllAddressByModifiedDate(modifieddate).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllAddressByStateProvince_test(){
        Address[] list = new Address[5];
        
        Integer stateprovince = 0;
        Stateprovince sp = new Stateprovince();
        sp.setStateprovinceid(stateprovince);

        for (int i = 0; i < list.length; i++) {
            Address p = new Address();
            p.setStateprovince(sp);
            list[i] = p;
        }

        when( restTemplate.getForObject(ADDR_URL+
                "search/findAllByStateProvince?stateprovince=" + stateprovince, Address[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllAddressByStateProvince(stateprovince).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllAddress_test(){
        Address[] list = new Address[5];
        

        for (int i = 0; i < list.length; i++) {
            Address p = new Address();
            list[i] = p;
        }

        when( restTemplate.getForObject(ADDR_URL, Address[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllAddresses().size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAddressById_test(){
        Address bs = new Address();
        
        when(restTemplate.getForObject(ADDR_URL+bs.getAddressid(), Address.class))
        		.thenReturn(bs);


        assertEquals(bd.findAddressById(bs.getAddressid()).getAddressid(), bs.getAddressid(),
                "Unexpected id");
    }
    
    @Test
    public void deleteAddress_test(){
    	Address ben = new Address();

        bd.deleteAddress(ben);

        verify(restTemplate).delete(ADDR_URL+ben.getAddressid());
    }
    
    
    @Test
    public void saveAddress_test(){
    	Address ben = new Address();
        HttpEntity<Address> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(ADDR_URL, request, Address.class))
                .thenReturn(ben);
        
        assertEquals(bd.saveAddress(ben).getAddressid(), ben.getAddressid(), "Unexpected id");
    }
    
    @Test
    public void editAddress_test(){
    	Address ben = new Address();

        bd.editAddress(ben);

        restTemplate.put(ADDR_URL, ben, Address.class);
    }
    
    //Addresstype --------------------------------------------------------------------------------------------
	
    @Test
    public void findAllAddresstype_test(){
        Addresstype[] list = new Addresstype[5];
        

        for (int i = 0; i < list.length; i++) {
            Addresstype p = new Addresstype();
            list[i] = p;
        }

        when( restTemplate.getForObject(ADDR_TYPE_URL, Addresstype[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllAddresstypes().size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAddresstypeById_test(){
        Addresstype bs = new Addresstype();
        
        when(restTemplate.getForObject(ADDR_TYPE_URL+bs.getAddresstypeid(), Addresstype.class))
        		.thenReturn(bs);


        assertEquals(bd.findAddresstypeById(bs.getAddresstypeid()).getAddresstypeid(), bs.getAddresstypeid(),
                "Unexpected id");
    }
    
    @Test
    public void deleteAddresstype_test(){
    	Addresstype ben = new Addresstype();

        bd.deleteAddresstype(ben);

        verify(restTemplate).delete(ADDR_TYPE_URL+ben.getAddresstypeid());
    }
    
    
    @Test
    public void saveAddresstype_test(){
    	Addresstype ben = new Addresstype();
        HttpEntity<Addresstype> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(ADDR_TYPE_URL, request, Addresstype.class))
                .thenReturn(ben);
        
        assertEquals(bd.saveAddresstype(ben).getAddresstypeid(), ben.getAddresstypeid(), "Unexpected id");
    }
    
    @Test
    public void editAddresstype_test(){
    	Addresstype ben = new Addresstype();

        bd.editAddresstype(ben);

        restTemplate.put(ADDR_TYPE_URL, ben, Addresstype.class);
    }
    
  //Phonenumbertype --------------------------------------------------------------------------------------------
	
    @Test
    public void findAllPhonenumbertype_test(){
        Phonenumbertype[] list = new Phonenumbertype[5];
        

        for (int i = 0; i < list.length; i++) {
            Phonenumbertype p = new Phonenumbertype();
            list[i] = p;
        }

        when( restTemplate.getForObject(PHONE_TYPE_URL, Phonenumbertype[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPhonenumbertypes().size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findPhonenumbertypeById_test(){
        Phonenumbertype bs = new Phonenumbertype();
        
        when(restTemplate.getForObject(PHONE_TYPE_URL+bs.getPhonenumbertypeid(), Phonenumbertype.class))
        		.thenReturn(bs);


        assertEquals(bd.findPhonenumbertypeById(bs.getPhonenumbertypeid()).getPhonenumbertypeid(), bs.getPhonenumbertypeid(),
                "Unexpected id");
    }
    
    @Test
    public void deletePhonenumbertype_test(){
    	Phonenumbertype ben = new Phonenumbertype();

        bd.deletePhonenumbertype(ben);

        verify(restTemplate).delete(PHONE_TYPE_URL+ben.getPhonenumbertypeid());
    }
    
    
    @Test
    public void savePhonenumbertype_test(){
    	Phonenumbertype ben = new Phonenumbertype();
        HttpEntity<Phonenumbertype> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(PHONE_TYPE_URL, request, Phonenumbertype.class))
                .thenReturn(ben);
        
        assertEquals(bd.savePhonenumbertype(ben).getPhonenumbertypeid(), ben.getPhonenumbertypeid(), "Unexpected id");
    }
    
    @Test
    public void editPhonenumbertype_test(){
    	Phonenumbertype ben = new Phonenumbertype();

        bd.editPhonenumbertype(ben);

        restTemplate.put(PHONE_TYPE_URL, ben, Phonenumbertype.class);
    }
    
  //Personphone --------------------------------------------------------------------------------------------
	
    @Test
    public void findAllPersonphonesByPhonenumbertype_test(){
        Personphone[] list = new Personphone[5];
        Phonenumbertype ptype = new Phonenumbertype();
        ptype.setPhonenumbertypeid(0);
        
        for (int i = 0; i < list.length; i++) {
            Personphone p = new Personphone();
            p.setPhonenumbertype(ptype);
            list[i] = p;
        }

        when(restTemplate.getForObject(PHONE_URL+
                "search/findAllByPhoneNumberType?phonenumbertype=" + ptype.getPhonenumbertypeid(), Personphone[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersonphonesByPhonenumbertype(ptype.getPhonenumbertypeid()).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllPersonPhonesByPerson_test(){
        Personphone[] list = new Personphone[5];
        Person per = new Person();
        per.setBusinessentityid(0);
        
        for (int i = 0; i < list.length; i++) {
            Personphone p = new Personphone();
            p.setPerson(per);
            list[i] = p;
        }

        when(restTemplate.getForObject(PHONE_URL+
                "search/findAllByPerson?person=" + per.getBusinessentityid(), Personphone[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersonPhonesByPerson(per.getBusinessentityid()).size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findAllPersonphone_test(){
        Personphone[] list = new Personphone[5];
        

        for (int i = 0; i < list.length; i++) {
            Personphone p = new Personphone();
            list[i] = p;
        }

        when( restTemplate.getForObject(PHONE_URL, Personphone[].class)
        		).thenReturn(list);


        assertEquals(bd.findAllPersonphones().size(), 5,
                "Unexpected size");
    }
    
    @Test
    public void findPersonphoneById_test(){
        Personphone bs = new Personphone();
    	PersonphonePK bsPK = new PersonphonePK();
    	bsPK.setBusinessentityid(0);
    	bsPK.setPhonenumbertypeid(0);
    	bsPK.setPhonenumber("312331123");
    	bs.setId(bsPK);
        
        when(restTemplate.getForObject(PHONE_URL+bsPK.getBusinessentityid()+"&"+bsPK.getPhonenumbertypeid(), Personphone.class))
        		.thenReturn(bs);


        assertEquals(bd.findPersonphoneById(bsPK).getId(), bs.getId(),
                "Unexpected id");
    }
    
    @Test
    public void deletePersonphone_test(){
    	Personphone ben = new Personphone();
    	PersonphonePK benPK = new PersonphonePK();
    	benPK.setBusinessentityid(0);
    	benPK.setPhonenumbertypeid(0);
    	benPK.setPhonenumber("312331123");
    	ben.setId(benPK);
    	
        bd.deletePersonphone(ben);

        verify(restTemplate).delete(PHONE_URL+ben.getId().getBusinessentityid()+"&"+ben.getId().getPhonenumbertypeid());
    }
    
    
    @Test
    public void savePersonphone_test(){
    	Personphone ben = new Personphone();
        HttpEntity<Personphone> request = new HttpEntity<>(ben);

        when(restTemplate.postForObject(PHONE_URL, request, Personphone.class))
                .thenReturn(ben);
        
        assertEquals(bd.savePersonphone(ben).getId(), ben.getId(), "Unexpected id");
    }
    
    @Test
    public void editPersonphone_test(){
    	Personphone ben = new Personphone();

        bd.editPersonphone(ben);

        restTemplate.put(PHONE_URL, ben, Personphone.class);
    }
    
}
