package com.customer.remote.http.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import kotlin.jvm.JvmStatic;

public class ItemTypeAdapterFactory implements TypeAdapterFactory {

  private static final String RESPONSE_TAG_DATA = "data";

  @JvmStatic
  public static TypeAdapterFactory newInstance() {
    return new ItemTypeAdapterFactory();
  }

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
    TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
    return new ItemTypeAdapter(delegate, elementAdapter).nullSafe();
  }

  class ItemTypeAdapter<T> extends TypeAdapter<T> {

    private TypeAdapter<T> delegate;
    private TypeAdapter<JsonElement> elementAdapter;

    ItemTypeAdapter(TypeAdapter<T> delegate, TypeAdapter<JsonElement> elementAdapter) {
      this.delegate = delegate;
      this.elementAdapter = elementAdapter;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
      delegate.write(out, value);
    }

    @Override
    public T read(JsonReader in) throws IOException {

      JsonElement jsonElement = elementAdapter.read(in);

      if (jsonElement.isJsonObject()) {
        T response;
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("data")
            && !jsonObject.get("data").isJsonArray()
            && !jsonObject.get("data").isJsonNull()) {
          response = getResponse(jsonObject);
        } else {
          return delegate.fromJsonTree(jsonElement);
        }

        try {
          validateResponse(response);
        } catch (NetworkException e) {
          e.printStackTrace();
        }
        return response;
      }

      return delegate.fromJsonTree(jsonElement);
    }

    private void validateResponse(T response) throws NetworkException {
      if (!(response instanceof ValidItem)) {
        // If response doesn't implement ValidItem, throw NetworkException
        String message = "Model must implement ValidItem";
        throw new NetworkException(new NetworkException.Error.InvalidModelType(), message);
      } else if (!((ValidItem) response).isValid()) {
        // If response is in not valid, throw NetworkException
        String message = "Invalid Model from Network Layer";
        throw new NetworkException(new NetworkException.Error.InvalidModel(), message);
      }
    }

    private T getResponse(JsonObject jsonObject) {
      if (jsonObject.has(RESPONSE_TAG_DATA)) {
        return delegate.fromJsonTree(jsonObject.get(RESPONSE_TAG_DATA));
      } else {
        return delegate.fromJsonTree(jsonObject);
      }
    }
  }
}
