package ma.nemo.assignment.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;


    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto toDTO(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public Product toEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }
}
