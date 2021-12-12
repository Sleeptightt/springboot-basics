package com.example.demo;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.front.model.person.UserApp;
import com.example.demo.front.model.person.UserType;
import com.example.demo.back.service.interfaces.AddressService;
import com.example.demo.back.service.interfaces.AddressTypeService;
import com.example.demo.back.service.interfaces.BusinessEntityService;
import com.example.demo.back.service.interfaces.PersonService;
import com.example.demo.back.service.interfaces.PhoneNumberTypeService;
import com.example.demo.back.service.interfaces.StateProvinceService;
import com.example.demo.back.service.interfaces.UserService;

import lombok.extern.java.Log;

@Log
@SpringBootApplication
public class Taller1Application {

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Taller1Application.class, args);
	}

	@Bean
	public CommandLineRunner dummy(UserService userService, BusinessEntityService benService, PersonService personService, StateProvinceService stprovService,
			AddressService addrService, AddressTypeService addrtypeService, PhoneNumberTypeService phonetypeService) {
		return (args) -> {
				UserApp u = new UserApp(1,"sleep","{noop}123", UserType.ADMINISTRATOR);
				UserApp u2 = new UserApp(2,"slee","{noop}123", UserType.OPERATOR);
				userService.save(u); userService.save(u2);
				Businessentity ben = new Businessentity();
				ben = benService.saveBusinessEntity(ben);
				Person p = new Person();
				p.setFirstname("Cesar"); p.setMiddlename("Leonardo"); p.setLastname("Canales"); p.setAdditionalcontactinfo("cesarcanales80@gmail.com"); p.setTitle("Ing");
				p.setBusinessentity(ben); 
				p = personService.savePerson(p);
				log.info((ben.getPerson() == null) + "");
				Stateprovince stprov = new Stateprovince();
				stprov.setName("Cali"); stprov.setStateprovincecode("CAL"); stprov.setTerritoryid(12);
				stprov = stprovService.saveStateprovince(stprov);
				Address addr = new Address();
				addr.setAddressline1("Calle 16#25A50"); addr.setAddressline2("Carrera 26#21 B 50"); addr.setCity("Cali"); addr.setPostalcode("760013");
				addr.setStateprovince(stprov);
				addr = addrService.saveAddress(addr);
				Addresstype addrtype = new Addresstype();
				addrtype.setName("Normal");
				addrtype = addrtypeService.saveAddressType(addrtype);
				Phonenumbertype phonetype = new Phonenumbertype();
				phonetype.setName("Normal");
				phonetype = phonetypeService.savePhoneNumberType(phonetype);
		};

	}
	
}
