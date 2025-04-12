package com.example.junggoheaven.domain.product.exception;

public class ProductNotFoundException extends ProductException {
  public ProductNotFoundException() {
    super(ProductErrorCode.NOT_FOUND_PRODUCT);
  }
}
