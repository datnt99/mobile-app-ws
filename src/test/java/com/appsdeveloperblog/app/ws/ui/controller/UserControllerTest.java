package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    private UserDto userDto;

    private final String userId = "bg23ks9uio";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDto = new UserDto();
        userDto.setFirstName("Dat");
        userDto.setLastName("Nguyen");
        userDto.setEmail("cortana775@gmail.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setUserId(userId);
        userDto.setAddresses(getAddressDTOs());
        userDto.setEncryptedPassword("xs3s3jj3a");
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(userId);
        assertNotNull(userRest);
        assertEquals(userId, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
    }

    private List<AddressDto> getAddressDTOs(){
        AddressDto shippingAddressDto = new AddressDto();
        shippingAddressDto.setType("shipping");
        shippingAddressDto.setCity("Vancouver");
        shippingAddressDto.setCountry("Canada");
        shippingAddressDto.setPostalCode("ABC123");
        shippingAddressDto.setStreetName("123 name");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("billing");
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreetName("123 name");

        List<AddressDto> addressDtos = new ArrayList<>();
        addressDtos.add(shippingAddressDto);
        addressDtos.add(billingAddressDto);

        return addressDtos;
    }
}