package com.mncm.daoimpl;

import com.api.common.entity.address.Address;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.model.address.AddressModel;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.AddressDao;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Slf4j
public class AddressDaoImpl extends OfyService implements AddressDao{
    @Override
    public Address get(String id) {
        return get(Address.class, id);
    }

    @Override
    public Address createNew(AddressModel addressModel) throws Exception{

        if(addressModel == null)
            return null;

        Address address = new Address(addressModel);

        if (addressModel.getId() != null) {
            address.setId(addressModel.getId());
        } else {
            address.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        return saveAddress(address);

    }

    @Override
    public List<Address> createBulk(List<AddressModel> addressModelList) throws Exception{

        List<Address> contactMethodList = new ArrayList<>();
        if(addressModelList == null)
            return contactMethodList;

        Iterator iterator = addressModelList.iterator();

        while (iterator.hasNext()){
            AddressModel addressModel = (AddressModel) iterator.next();
            if (addressModel.getId() == null){
                addressModel.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
            }

            Address address = new Address(addressModel);
            address = saveAddress(address);

            contactMethodList.add(address);
        }

        return contactMethodList;
    }

    @Override
    public Address updateAddress(Address address) throws Exception {

        return saveAddress(address);

    }

    @Override
    public Address saveAddress(Address address) {

        return save(address) != null ? address : null;

    }

    @Override
    public Address deleteAddress(String id) {

        Address address = get(id);

        if (address == null)
            return null;

        return delete(address) ? address : null;

    }
}
