package com.customer.data.mapper;

import com.customer.domain.model.location.IpAddressToLocationData;
import com.customer.domain.model.location.LocationData;
import com.customer.remote.http.model.response.location.IpAddressToLocationDetails;

public class IpAddressToLocationMapper {

  public IpAddressToLocationData mapper(IpAddressToLocationDetails details) {
    return new IpAddressToLocationData(details.getIp(), details.getCity(), details.getRegion(),
        details.getCountry(), details.getPostal(), details.getTimezone(),
        details.getLocationData() != null ? new LocationData(details.getLocationData().getLatitude(),
            details.getLocationData().getLongitude()) : null);
  }
}
