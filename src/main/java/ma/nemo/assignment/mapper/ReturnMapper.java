package ma.nemo.assignment.mapper;

import ma.nemo.assignment.domain.Return;
import ma.nemo.assignment.dto.ReturnDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReturnMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ReturnMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReturnDto toDTO(Return returnProduct) {
        return modelMapper.map(returnProduct, ReturnDto.class);
    }

    public Return toEntity(ReturnDto returnProductDTO) {
        return modelMapper.map(returnProductDTO, Return.class);
    }
}
