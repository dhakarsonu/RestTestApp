package com.mncm.config;

import com.api.common.entity.IM.Product;
import com.api.common.entity.OM.Order;
import com.api.common.entity.OM.OrderItem;
import com.api.common.entity.OM.Track;
import com.api.common.entity.address.Address;
import com.api.common.entity.common.*;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.entity.contact.UserRole;
import com.mncm.endpoints.api.*;
import com.mncm.exception.mapper.ResourceNotFoundExceptionMapper;
import com.mncm.filter.AccessTokenFilter;
import com.mncm.filter.ApiLoggingFilter;
import com.mncm.filter.CommonApiResponseFilter;
import com.mncm.exception.mapper.ForbiddenExceptionMapper;
import com.mncm.exception.mapper.GenericExceptionMapper;
import com.mncm.exception.mapper.IllegalArgExceptionMapper;
import com.mncm.exception.mapper.InvalidFormatExceptionMapper;
import com.api.common.services.objectify.OfyService;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import com.googlecode.objectify.util.jackson.ObjectifyJacksonModule;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

import com.mncm.exception.mapper.JaxRsForbiddenExceptionMapper;
import com.mncm.exception.mapper.NotAllowedExceptionMapper;

/**
 * Created by sonudhakar on 16/07/17.
 */
public class AppConfig extends Application {

    public AppConfig() {
        //registerOfyEntities();
    }

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();

        classes.add(JacksonObjectResolver.class);
        classes.add(ObjectifyJacksonModule.class);

        registerApis(classes);
        registerOfyEntities();

        return classes;
    }

    @Override
    public Set<Object> getSingletons() {

        Set<Object> singletons = new HashSet<>();
        singletons.add(new ApiLoggingFilter());
        singletons.add(new CommonApiResponseFilter());
        singletons.add(new AccessTokenFilter());

        //exception mappers
        singletons.add(new IllegalArgExceptionMapper());
        singletons.add(new ResourceNotFoundExceptionMapper());
        singletons.add(new ForbiddenExceptionMapper());
        singletons.add(new NotAllowedExceptionMapper());
        singletons.add(new JaxRsForbiddenExceptionMapper());
        singletons.add(new InvalidFormatExceptionMapper());
        singletons.add(new GenericExceptionMapper());

        // for now allowing all based on requested origins
        CorsFilter cors = new CorsFilter();
        cors.getAllowedOrigins().add("*");
        cors.setCorsMaxAge(1728000);
        cors.setAllowCredentials(false);

        singletons.add(cors);
        return singletons;
    }

    private void registerApis(Set<Class<?>> classes) {
        //TODO : register all api endpoints
        classes.add(CategoryEndPoint.class);
        classes.add(AppEndPoint.class);
        classes.add(InventoryEndPoint.class);
        classes.add(AccountEndPoint.class);
        classes.add(AppEndPoint.class);
       // classes.add(CartEndPoint.class);
        classes.add(CategoryEndPoint.class);
        classes.add(FileUploadEndpoint.class);
        //classes.add(InviteEndPoint.class);
        classes.add(LoginEndPoint.class);
        classes.add(OrderEndPoint.class);
        classes.add(ProductEndPoint.class);
        classes.add(RegistrationEndPoint.class);
        classes.add(TrackEndPoint.class);
        classes.add(UserEndPoint.class);
        classes.add(CartEndPoint.class);
        classes.add(AuthenticationApi.class);
    }


    private void registerOfyEntities() {

        //TODO : Register all persistable entity JDO

        OfyService.factory().register(Category.class);
        OfyService.factory().register(App.class);
        OfyService.factory().register(Address.class);
        OfyService.factory().register(Account.class);
        OfyService.factory().register(Cart.class);
        //OfyService.factory().register(Invitation.class);
        OfyService.factory().register(Product.class);
        OfyService.factory().register(Contact.class);
        OfyService.factory().register(ContactMethod.class);
        OfyService.factory().register(UserRole.class);
        OfyService.factory().register(Order.class);
        OfyService.factory().register(Track.class);
        OfyService.factory().register(PaymentMethod.class);
        OfyService.factory().register(OrderItem.class);
        OfyService.factory().register(Token.class);

    }
}
