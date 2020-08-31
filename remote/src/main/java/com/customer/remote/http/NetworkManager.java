package com.customer.remote.http;

public interface NetworkManager {

  ApiHandler nodeApiHandler();

  PythonApiHandler pythonApiHandler();

  PromoCodeApiHandler promocodeApiHandler();

}
