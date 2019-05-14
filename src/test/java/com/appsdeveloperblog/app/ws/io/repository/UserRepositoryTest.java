package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private static boolean recordCreated = false;

    @BeforeEach
    void setUp() {
        if (!recordCreated) createRecords();
    }

    @Test
    @Ignore
    final void testGetVerifiedUsers() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 2);
    }

    @Test
    final void testFindUsersByFirstName() {
        String firstName = "Dat";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity userEntity = users.get(0);
        assertEquals(firstName, userEntity.getFirstName());
    }

    @Test
    final void testFindUsersByLastName() {
        String lastName = "Nguyen";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size() == 1);

        UserEntity userEntity = users.get(0);
        assertEquals(lastName, userEntity.getLastName());
    }

    @Test
    final void testFindUsersByKeyword() {
        String keyword = "at";
        List<UserEntity> users = userRepository.findUserByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity userEntity = users.get(0);
        assertTrue(userEntity.getLastName().contains(keyword)
                || userEntity.getFirstName().contains(keyword));
    }

    @Test
    final void testFindUserByFirstNameAndLastName() {
        String keyword = "at";
        List<Object[]> users = userRepository.findUserByFirstNameAndLastName(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        Object[] user = users.get(0);
        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("User first name: " + userFirstName);
        System.out.println("User last name: " + userLastName);
    }

    @Test
    final void testUpdateEmailVerificationStatus() {
        boolean emailVerificationStatus = false;
        userRepository.updateUserEmailVerificationStatus(emailVerificationStatus, "xxx");

        UserEntity storedUserDetails = userRepository.findByUserId("xxx");
        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
        assertTrue(storedEmailVerificationStatus == emailVerificationStatus);
    }

    @Test
    final void testFindUserEntityByUserId() {
        String userId = "xxx";
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);

        assertNotNull(userEntity);
        assertTrue(userEntity.getUserId().equals(userId));
    }

    @Test
    final void testGetUserEntityFullNameById() {
        String userId = "xxx";
        List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

        assertNotNull(records);
        assertTrue(records.size() == 1);

        Object[] user = records.get(0);
        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);
    }

    @Test
    final void testUpdateUserEntityEmailVerificationStatus() {
        boolean emailVerificationStatus = true;
        userRepository.updateUserEntityEmailVerificationStatus(emailVerificationStatus, "xxx");

        UserEntity storedUserDetails = userRepository.findByUserId("xxx");
        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
        assertTrue(storedEmailVerificationStatus == emailVerificationStatus);
    }

    private void createRecords() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Dat");
        userEntity.setLastName("Le");
        userEntity.setUserId("xxx");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setPassword("123456");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationStatus(true);

        AddressEntity shippingAddressEntity = new AddressEntity();
        shippingAddressEntity.setType("shipping");
        shippingAddressEntity.setAddressId("sd8sdf34hd");
        shippingAddressEntity.setCity("Vancouver");
        shippingAddressEntity.setCountry("Canada");
        shippingAddressEntity.setPostalCode("ABC123");
        shippingAddressEntity.setStreetName("123 name");

        AddressEntity billingAddressEntity = new AddressEntity();
        billingAddressEntity.setType("billing");
        billingAddressEntity.setAddressId("sd83ff1a4hd2");
        billingAddressEntity.setCity("Vancouver");
        billingAddressEntity.setCountry("Canada");
        billingAddressEntity.setPostalCode("ABC123");
        billingAddressEntity.setStreetName("123 name");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(shippingAddressEntity);
        addresses.add(billingAddressEntity);
        userEntity.setAddresses(addresses);
        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Dat");
        userEntity2.setLastName("Nguyen");
        userEntity2.setUserId("xxx2");
        userEntity2.setEncryptedPassword("7a23f4sj33h2");
        userEntity2.setPassword("123456");
        userEntity2.setEmail("test1@test.com");
        userEntity2.setEmailVerificationStatus(true);

        AddressEntity shippingAddressEntity2 = new AddressEntity();
        shippingAddressEntity2.setType("shipping");
        shippingAddressEntity2.setAddressId("sd834hfd");
        shippingAddressEntity2.setCity("Vancouver");
        shippingAddressEntity2.setCountry("Canada");
        shippingAddressEntity2.setPostalCode("ABC123");
        shippingAddressEntity2.setStreetName("123 name");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(shippingAddressEntity2);
        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);
        recordCreated = true;
    }
}