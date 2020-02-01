package controller;

import model.Customer;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import service.CustomerService;


@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //-------------------Retrieve All Customers--------------------------------------------------------

    @RequestMapping(value = "/customers/", method = RequestMethod.GET)
    public ResponseEntity listAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(customers.getContent(), HttpStatus.OK);
    }

    //-------------------Retrieve Single Customer--------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCustomer(@PathVariable("id") long id) {
        JSONObject object = new JSONObject();
        System.out.println("Fetching Customer with id " + id);
        Customer customer = customerService.findById(id);
        if (customer == null) {
            System.out.println("Customer with id " + id + " not found");
            Error error = new Error("Customer with id " + id + " not found");
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        object.put("Customer",customer);
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
    //-------------------Create a Customer--------------------------------------------------------

    @RequestMapping(value = "/customers/", method = RequestMethod.POST)
    public ResponseEntity createCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
        JSONObject object = new JSONObject();
        System.out.println("Creating Customer " + customer.getLastName());
        customerService.save(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri());
        object.put("name","Son");
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    //------------------- Update a Customer --------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        System.out.println("Updating Customer " + id);

        Customer currentCustomer = customerService.findById(id);

        if (currentCustomer == null) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());

        customerService.save(currentCustomer);
        return new ResponseEntity<>(currentCustomer, HttpStatus.OK);
    }

    //------------------- Delete a Customer --------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCustomer(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Customer with id " + id);

        Customer customer = customerService.findById(id);
        if (customer == null) {
            System.out.println("Unable to delete. Customer with id " + id + " not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        customerService.remove(id);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/customers/find",method = RequestMethod.GET)
    public ModelAndView findCustomer(){
        return new ModelAndView("/customer/find");
    }
    @RequestMapping(value = "/customers/find",method = RequestMethod.POST)
    public ResponseEntity<JSONObject> findCustomerByName(@RequestBody String lastName,Pageable pageable,Errors errors){
        JSONObject object = new JSONObject();
        if (errors.hasErrors()) {
            object.put("errors",errors);
        }
        if (lastName == null){
            object.put("error",new Error("Sth is null"));
        }else {
            if (lastName.equals("")) {
                object.put("error","Your input is empty");
            }else {
                Page<Customer> customers = customerService.findByLastName(lastName,pageable);
                if (customers.isEmpty()){
                    object.put("error","This customer is not exist");
                }else {
                    object.put("customers",customers.getContent());
                    object.put("sizeCustomers",customers.getNumberOfElements());
                }

            }
        }
       return new ResponseEntity<>(object,HttpStatus.OK);
    }
}
