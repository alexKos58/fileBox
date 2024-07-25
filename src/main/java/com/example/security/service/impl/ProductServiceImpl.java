package com.example.security.service.impl;

import com.example.security.controller.dto.request.ProductRequestDto;
import com.example.security.controller.dto.response.ProductResponseDto;
import com.example.security.domain.entity.Product;
import com.example.security.domain.repository.ProductRepository;
import com.example.security.mapper.ProductMapper;
import com.example.security.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponseDto> getAllProduct() {
        return productMapper.toResponseList(productRepository.findAll());
    }


    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto requestDto) {
        Product product = productMapper.toProduct(requestDto);
        product.setImageList(null);
        product.setError(null);
        product.setPersonList(null);
        product.setOrderList(null);
        productRepository.saveAndFlush(product);
        return productMapper.toProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(int id, ProductRequestDto requestDto) {
        Product productOld = productRepository.findById(id).orElseThrow();
        Product productNew = productMapper.toProduct(requestDto);
        productNew.setId(productOld.getId());
        productNew.setOrderList(productOld.getOrderList());
        productNew.setPersonList(productOld.getPersonList());
        productNew.setImageList(productOld.getImageList());
        productNew.setError(productOld.getError());
        productNew.setDateTime(productOld.getDateTime());
        productRepository.save(productNew);
        return productMapper.toProductResponseDto(productNew);
    }

    @Transactional
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.deleteById(product.getId());
    }
}
