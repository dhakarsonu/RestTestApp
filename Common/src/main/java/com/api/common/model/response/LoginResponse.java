package com.api.common.model.response;

import com.api.common.entity.common.Account;
import com.api.common.entity.contact.Contact;
import com.api.common.enums.AccountType;
import com.api.common.enums.Role;
import lombok.Data;

/**
 * Created by sonudhakar on 25/03/18.
 */
@Data
public class LoginResponse {

    private Contact contact;

    private Role role;

    private AccountResponseModel accountResponse;

    private Account account;

    private ContactResponse contactResponse;
}
