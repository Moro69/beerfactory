package com.moro.soap.interfaces;

import com.moro.model.Beer;
import com.moro.model.BeerDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;


@WebService(targetNamespace = "http://moro.com/soap/beersoapservice")
@SOAPBinding
public interface BeerEndpoint {
    /**
     * Get beers collection.
     *
     * @return beers collection.
     */
    @WebMethod(action = "getAllBeers")
    ArrayList<BeerDto> getAllBeers();

    /**
     * Get beer By Id.
     *
     * @param beerId id.
     * @return beer.
     */
    @WebMethod(action = "getBeerById")
    Beer getBeerById(@WebParam(name = "beerId") @XmlElement(required=true) Integer beerId);

    /**
     * Persist new beer.
     *
     * @param beer new beer.
     * @return beer with id.
     */
    @WebMethod(action = "addBeer")
    Beer addBeer(@WebParam(name = "beer") @XmlElement(required=true) Beer beer);

    /**
     * Update beer.
     *
     * @param beer beer.
     */
    @WebMethod(action = "updateBeer")
    void updateBeer(@WebParam(name = "beer") @XmlElement(required=true) Beer beer);

    /**
     * Delete beer.
     *
     * @param beerId beer id.
     */
    @WebMethod(action = "deletrBeerById")
    void deleteBeerById(@WebParam(name = "beerId") @XmlElement(required=true) Integer beerId);
}
