package com.api.common.model.response;

import com.api.common.entity.common.Account;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.UserRole;
import com.api.common.enums.AccountType;
import com.api.common.enums.Role;
import lombok.Data;

/**
 * Created by sonudhakar on 24/03/18.
 */
@Data
public class SignupResponse {

    private Contact contact;

    private Role role;

    private AccountResponseModel accountResponse;

    private Account account;
}
