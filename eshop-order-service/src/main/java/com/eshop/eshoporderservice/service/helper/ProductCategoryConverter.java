package com.eshop.eshoporderservice.service.helper;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.eshop.eshoporderservice.model.inventory.ProductCategory;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Product Category Enumeration Converter for Database Column and Entity Attribute
 */

@Service
@Converter(autoApply = true)
public class ProductCategoryConverter implements AttributeConverter<ProductCategory, String> {

    @Override
    public String convertToDatabaseColumn(ProductCategory category) {
        if (category == null) {
            return null;
        }
        return category.getCategory();
    }

    @Override
    public ProductCategory convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ProductCategory.values())
          .filter(c -> c.getCategory().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
	
}
