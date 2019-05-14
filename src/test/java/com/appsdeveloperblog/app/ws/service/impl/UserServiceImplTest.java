package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.AmazonSES;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AmazonSES amazonSES;

    private String userId = "js3dhf82734";
    private String encryptPassword = "76324sj3kh2";

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Dat");
        userEntity.setLastName("Le");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptPassword);
        userEntity.setEmail("cortana775@gmail.com");
        userEntity.setEmailVerificationToken("8sd8h2d");
        userEntity.setAddresses(getAddressEntities());
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("cortana775@gmail.com");
        assertNotNull(userDto);
        assertEquals("Dat", userDto.getFirstName());
    }

    @Test
    final void testGetUser_UserNameNotFoundException(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                ()-> {
                    userService.getUser("cortana775@gmail.com");
                });
    }

    @Test
    void createUser(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("HI2SOSJd3sf");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressDTOs());
        userDto.setFirstName("Dat");
        userDto.setLastName("Le");
        userDto.setPassword("12345");
        userDto.setEmail("cortana775@gmail.com");

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());

        verify(utils, times(2)).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("12345");
        verify(userRepository, times(1)).save(any(UserEntity.class));
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

    private List<AddressEntity> getAddressEntities(){
        List<AddressDto> addressDtos = getAddressDTOs();

        Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
         return new ModelMapper().map(addressDtos, listType);
    }

    @Test
    final void testCreateUser_CreateUserException(){
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddressDTOs());
        userDto.setFirstName("Dat");
        userDto.setLastName("Le");
        userDto.setPassword("12345");
        userDto.setEmail("cortana777@gmail.com");

        assertThrows(UserServiceException.class,
                ()-> {
                    userService.createUser(userDto);
                });
    }

}