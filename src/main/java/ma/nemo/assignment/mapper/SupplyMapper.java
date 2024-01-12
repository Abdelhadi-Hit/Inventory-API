package ma.nemo.assignment.mapper;

import ma.nemo.assignment.domain.Supply;
import ma.nemo.assignment.dto.SupplyDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplyMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public SupplyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SupplyDto toDTO(Supply supply) {
        return modelMapper.map(supply, SupplyDto.class);
    }

    public Supply toEntity(SupplyDto supplyDTO) {
        return modelMapper.map(supplyDTO, Supply.class);
    }

}
