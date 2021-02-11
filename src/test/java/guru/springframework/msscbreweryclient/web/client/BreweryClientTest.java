package guru.springframework.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.*;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BreweryClientTest {

  @Autowired
  BreweryClient breweryClient;

  @Test
  void getBeerById() {
      BeerDto dto = breweryClient.getBeerById(UUID.randomUUID());

      assertNotNull(dto);
  }

  @Test
  void testAddBeer()
  {
    BeerDto beerDto = BeerDto.builder().beerName("DUFF").build();

    URI uri = breweryClient.addBeer(beerDto);

    System.out.println(uri);
  }

  @Test
  void testUpdateBeer() {
    BeerDto beerDto = BeerDto.builder().beerName("DUFF").build();

    breweryClient.updateBeer(UUID.randomUUID(), beerDto);
  }

  @Test
  void testDeleteBeer() {
    breweryClient.deleteBeer(UUID.randomUUID());
  }

  @Test
  void getCustomerById() {
    CustomerDto dto = breweryClient.getCustomerById(UUID.randomUUID());

    assertNotNull(dto);
  }

  @Test
  void testAddCustomer()
  {
    CustomerDto customerDto = CustomerDto.builder().name("Chris").build();

    URI uri = breweryClient.addCustomer(customerDto);

    System.out.println(uri);
  }

  @Test
  void testUpdateCustomer() {
    CustomerDto customerDto = CustomerDto.builder().name("Chris").build();

    breweryClient.updateCustomer(UUID.randomUUID(), customerDto);
  }

  @Test
  void testDeleteCustomer() {
    breweryClient.deleteCustomer(UUID.randomUUID());
  }


}