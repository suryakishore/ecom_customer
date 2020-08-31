package com.customer.remote.http;

class IpConfigApiHandler implements IpConfigApi {
  private IpConfigApi mIpConfigApi;

  IpConfigApiHandler(IpConfigApi ipConfigApi) {
    mIpConfigApi = ipConfigApi;
  }
}
