package org.asymptotes.ecommerce.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.asymptotes.ecommerce.customer.Customer;
import org.asymptotes.ecommerce.exception.CustomerNotFoundException;
import org.asymptotes.ecommerce.repository.CustomerRepository;
import org.asymptotes.ecommerce.dto.CustomerRequest;
import org.asymptotes.ecommerce.dto.CustomerResponse;
import org.asymptotes.ecommerce.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = repository.findById(request.id()).orElseThrow(() -> new CustomerNotFoundException(
                format("Cannot update customer::No customer found with the provided ID::%s ", request.id())
        ));
        mergerCustomer(customer, request);
        repository.save(customer);
    }

    private void mergerCustomer(Customer customer, @Valid CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setFirstName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setFirstName(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }

    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream().map(mapper::fromCustomer).collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId).map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("No customer found with the provided ID :: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);

    }
}
