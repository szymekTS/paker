package pl.szymanski.paker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szymanski.paker.models.Customer;
import pl.szymanski.paker.payload.request.CustomerRequest;
import pl.szymanski.paker.payload.response.CustomerResponse;
import pl.szymanski.paker.payload.response.MessageResponse;
import pl.szymanski.paker.repository.CustomerRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customer_R;

    public ResponseEntity<?> findAll() {
        List<CustomerResponse> responseList = new ArrayList<>();

        for (Customer u : customer_R.findAll()) {
            responseList.add(customerToCustomerResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }


    public ResponseEntity<?> findById(String id) {
        Optional<Customer> customerOptional = customer_R.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerResponse response = customerToCustomerResponse(customer);
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not Found"));
    }

    public ResponseEntity<?> findByName(String name) {
        List<CustomerResponse> responseList = new ArrayList<>();

        for( Customer u : customer_R.findByNameRegex(name)) {
            responseList.add(customerToCustomerResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findBySurname(String surname) {
        List<CustomerResponse> responseList = new ArrayList<>();

        for (Customer u : customer_R.findBySurnameRegex(surname)) {
            responseList.add(customerToCustomerResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> findByEmail(String email) {
        List<CustomerResponse> responseList = new ArrayList<>();

        for (Customer u : customer_R.findByEmailRegex(email)) {
            responseList.add(customerToCustomerResponse(u));
        }
        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> addCustomer(CustomerRequest newCustomer) {
        if (newCustomer.isValid()) {

            Customer customer = customerRequestToCustomer(newCustomer);
            if (customer_R.existsByEmail(customer.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Email exist"));
            }
            customer_R.save(customer);
            return ResponseEntity.ok(customerToCustomerResponse(customer));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }

    public ResponseEntity<?> delUser(String id) {
        if (customer_R.existsById(id)) {
            customer_R.deleteById(id);
            return ResponseEntity
                    .ok(new MessageResponse("Deleted"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not found"));
    }

    public ResponseEntity<?> updateUser(CustomerRequest updatedUser) {
        if (updatedUser.isValid()) {
            Customer customer = customerRequestToCustomer(updatedUser);
            Optional<Customer> toUpdateOptional = customer_R.findByEmail(updatedUser.getEmail());
            if (toUpdateOptional.isPresent()) {
                Customer toUpdate = toUpdateOptional.get();
                toUpdate.setName(customer.getName());
                toUpdate.setSurname(customer.getSurname());
                customer_R.save(toUpdate);
                return ResponseEntity.ok(customerToCustomerResponse(toUpdate));
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Not found"));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Not valid request"));
    }


    private CustomerResponse customerToCustomerResponse(Customer u) {
        return new CustomerResponse(u.getId(),u.getName(),u.getSurname(), u.getEmail());
    }

    private Customer customerRequestToCustomer(CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setEmail(customerRequest.getEmail());
        return customer;
    }

}
