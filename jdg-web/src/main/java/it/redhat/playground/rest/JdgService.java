package it.redhat.playground.rest;

import it.redhat.playground.application.ApplicationConfig;
import it.redhat.playground.application.ConfigContainer;
import it.redhat.playground.application.JdgWebConfiguration;
import it.redhat.playground.bean.CacheData;
import it.redhat.playground.bean.JdgEntry;
import it.redhat.playground.service.*;
import org.infinispan.remoting.transport.Address;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by samuele on 2/24/15.
 */
@Path("/jdg")
public class JdgService {

    @GET
    @Path("/distprimary")
    @Produces(MediaType.APPLICATION_JSON)
    public CacheData getDistributionInfo() {


        Map<Address,Long> keyCountMap = CountKeyService.count(ConfigContainer.getInstance().getCache(),new LocatePrimaryService());
        return CountKeyService.toDTO(keyCountMap);
    }

    @GET
    @Path("/distall")
    @Produces(MediaType.APPLICATION_JSON)
    public CacheData getDistributionAllInfo() {


        Map<Address,Long> keyCountMap = CountKeyService.count(ConfigContainer.getInstance().getCache(), new LocateAllService());
        return CountKeyService.toDTO(keyCountMap);
    }

    @GET
    @Path("/address")
    @Produces({ "application/json" })
    public String address() {
        return "{\"result\":\"\"}";
    }

    @GET
    @Path("/hashtags")
    @Produces({ "application/json" })
    public String hashtags() {
        return "{\"result\":\"\"}";
    }

    @GET
    @Path("/get")
    @Produces({ "application/json" })
    public String get() {
        return "{\"result\":\"\"}";
    }


}
