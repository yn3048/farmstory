package kr.co.farmstory.service;

import kr.co.farmstory.dto.ProductDTO;


import kr.co.farmstory.entity.Product;

import kr.co.farmstory.mapper.ProductMapper;
import kr.co.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j @Service @RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ModelMapper modelMapper;


    public List<ProductDTO> selectProducts(String cate,String keyword) {
        return productMapper.selectProducts(cate, keyword);

    }

    //등록상품 view 보기
    public ProductDTO findById(int pno){

        return productMapper.selectProduct(pno);
    }

    //상품 등록
    public Product insertProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }



}
    //상품삭제


