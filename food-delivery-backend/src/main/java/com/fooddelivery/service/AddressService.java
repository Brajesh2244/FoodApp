package com.fooddelivery.service;

import com.fooddelivery.dto.AddressRequest;
import com.fooddelivery.entity.Address;
import com.fooddelivery.entity.User;
import com.fooddelivery.repository.AddressRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address createAddress(AddressRequest request) {

        User user;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        }

        if (request.isDefaultAddress()) {
            removeExistingDefaultAddress(user.getId());
        }

        Address address = new Address();

        address.setUser(user);
        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setLandmark(request.getLandmark());
        address.setDefaultAddress(request.isDefaultAddress());

        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));
    }

    public Address getDefaultAddress(Long userId) {
        return addressRepository
                .findByUserIdAndDefaultAddressTrue(userId)
                .orElseGet(() -> {
                    List<Address> list = addressRepository.findByUserId(userId);
                    return list.isEmpty() ? null : list.get(0);
                });
    }

    public Address updateAddress(Long id, AddressRequest request) {

        Address address = getAddressById(id);

        if (request.isDefaultAddress()) {
            removeExistingDefaultAddress(address.getUser().getId());
        }

        address.setAddressType(request.getAddressType());
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setLandmark(request.getLandmark());
        address.setDefaultAddress(request.isDefaultAddress());

        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {

        Address address = getAddressById(id);

        addressRepository.delete(address);
    }

    public Address setDefaultAddress(Long addressId, Long userId) {
        // Verify the address belongs to the user
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to the user");
        }
        // Clear existing default for this user
        removeExistingDefaultAddress(userId);
        // Set this address as default
        address.setDefaultAddress(true);
        return addressRepository.save(address);
    }


    private void removeExistingDefaultAddress(Long userId) {
        addressRepository
                .findByUserIdAndDefaultAddressTrue(userId)
                .ifPresent(address -> {
                    address.setDefaultAddress(false);
                    addressRepository.save(address);
                });
    }

}