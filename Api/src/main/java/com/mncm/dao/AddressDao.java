package com.mncm.dao;

import com.api.common.entity.address.Address;
import com.api.common.model.address.AddressModel;

import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 18/03/18.
 */
public interface AddressDao {
    public Address get(String id);
    public Address createNew(AddressModel addressModel) throws Exception;
    public Address updateAddress(Address address) throws Exception;
    public Address saveAddress(Address address);
    public Address deleteAddress(String id);
    public List<Address> createBulk(List<AddressModel> addressModelList) throws Exception;
}
