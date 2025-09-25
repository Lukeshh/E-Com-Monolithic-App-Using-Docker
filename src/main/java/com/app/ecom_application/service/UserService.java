package com.app.ecom_application.service;

import com.app.ecom_application.dto.AddressDto;
import com.app.ecom_application.dto.UserDtoRequest;
import com.app.ecom_application.dto.UserDtoResponse;

import com.app.ecom_application.entity.AddressEntity;
import com.app.ecom_application.entity.UserEntity;
import com.app.ecom_application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


        public UserDtoRequest createUser(UserDtoRequest dtoRequest) {
        UserEntity newUserEntity = new UserEntity();
            newUserEntity = mapUserDtoResquestToUser(dtoRequest);

        userRepository.save(newUserEntity);

        return  dtoRequest;
    }
    public boolean updateUser(UserDtoRequest userDtoRequest, Long id) {
        UserEntity findUserById = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserId not found"));

        if (userDtoRequest.getEmail() != null) {
            findUserById.setEmail(userDtoRequest.getEmail());
        }
        if (userDtoRequest.getFirstName() != null) {
            findUserById.setFirstName(userDtoRequest.getFirstName());
        }
        if (userDtoRequest.getLastName() != null) {
            findUserById.setLastName(userDtoRequest.getLastName());
        }
        if (userDtoRequest.getPhoneNumber() != null) {
            findUserById.setPhoneNumber(userDtoRequest.getPhoneNumber());
        }
        if (userDtoRequest.getRole() != null) {
            findUserById.setRole(userDtoRequest.getRole());
        }

        if (userDtoRequest.getAddress() != null) {
            AddressEntity addressEntity = findUserById.getAddress();
            if (addressEntity == null) {
                addressEntity = new AddressEntity();
            }
            if (userDtoRequest.getAddress().getCity() != null) {
                addressEntity.setCity(userDtoRequest.getAddress().getCity());
            }
            if (userDtoRequest.getAddress().getCountry() != null) {
                addressEntity.setCountry(userDtoRequest.getAddress().getCountry());
            }
            if (userDtoRequest.getAddress().getState() != null) {
                addressEntity.setState(userDtoRequest.getAddress().getState());
            }
            if (userDtoRequest.getAddress().getZip() != null) {
                addressEntity.setZip(userDtoRequest.getAddress().getZip());
            }
            if (userDtoRequest.getAddress().getStreet() != null) {
                addressEntity.setStreet(userDtoRequest.getAddress().getStreet());
            }
            findUserById.setAddress(addressEntity);
        }

        userRepository.save(findUserById);
        return true;
    }


    public UserDtoResponse findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse = mapToUserDtoResponse(userEntity);
        return userDtoResponse;
    }


//        public boolean updateUser(UserDtoRequest userDtoRequest, Long id) {
//           UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
//         if(userEntity.getId().equals(id) ){
//           userEntity = mapUserDtoResquestToUser(userDtoRequest);
//             userRepository.save(userEntity);
//             return true;
//         }
//           return false;
//
//        }

    public List<UserDtoResponse> findAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            UserDtoResponse userDtoResponse = mapToUserDtoResponse(userEntity);
            userDtoResponseList.add(userDtoResponse);
        }
        return userDtoResponseList;
        // return userRepository.findAll().stream().map(userEntity -> mapToUserDtoResponse(userEntity)).collect(Collectors.toList());
    }

    public UserDtoResponse mapToUserDtoResponse(UserEntity userEntity) {

        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(userEntity.getId());
        userDtoResponse.setFirstName(userEntity.getFirstName());
        userDtoResponse.setLastName(userEntity.getLastName());
        userDtoResponse.setEmail(userEntity.getEmail());
        userDtoResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userDtoResponse.setRole(userEntity.getRole());
        if (userEntity.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(userEntity.getAddress().getCity());
            addressDto.setCountry(userEntity.getAddress().getCountry());
            addressDto.setState(userEntity.getAddress().getState());
            addressDto.setZip(userEntity.getAddress().getZip());
            addressDto.setStreet(userEntity.getAddress().getStreet());
            userDtoResponse.setAddressDto(addressDto);
        }
        return userDtoResponse;
    }

    // Mapping UserDtoRequest to UserEntity i.e. creating user

    public UserEntity mapUserDtoResquestToUser(UserDtoRequest userDtoRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDtoRequest.getFirstName());
        userEntity.setLastName(userDtoRequest.getLastName());
        userEntity.setEmail(userDtoRequest.getEmail());
        userEntity.setPhoneNumber(userDtoRequest.getPhoneNumber());
        userEntity.setRole(userDtoRequest.getRole());
        if (userDtoRequest.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(userDtoRequest.getAddress().getCity());
            addressDto.setCountry(userDtoRequest.getAddress().getCountry());
            addressDto.setState(userDtoRequest.getAddress().getState());
            addressDto.setZip(userDtoRequest.getAddress().getZip());
            addressDto.setStreet(userDtoRequest.getAddress().getStreet());
            AddressEntity addressEntity = new AddressEntity();
            addressEntity = mapToAddressDto(addressDto);
            userEntity.setAddress(addressEntity);
        }
        return userEntity;
    }

    public AddressEntity mapToAddressDto(AddressDto addressDto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setState(addressDto.getState());
        addressEntity.setZip(addressDto.getZip());
        addressEntity.setStreet(addressDto.getStreet());
        return addressEntity;
    }


}
