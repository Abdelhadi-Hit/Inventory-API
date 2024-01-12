package ma.nemo.assignment.mapper;

import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.dto.TransactionHistoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionHistoryMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static TransactionHistoryDto toDTO(TransactionHistory transactionHistory){
        return modelMapper.map(transactionHistory, TransactionHistoryDto.class);
    }

    public static TransactionHistory toEntity(TransactionHistoryDto transactionHistoryDTO){
        return modelMapper.map(transactionHistoryDTO, TransactionHistory.class);
    }
}
