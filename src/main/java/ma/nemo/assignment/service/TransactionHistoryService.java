package ma.nemo.assignment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.domain.util.EventType;
import ma.nemo.assignment.dto.TransactionHistoryDto;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.TransactionHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class  TransactionHistoryService {
    private final TransactionHistoryMapper transactionMapper;
    private final TransactionHistoryRepository transactionHistoryRepository;


    @Transactional
    public TransactionHistoryDto createTransaction(Product product, EventType eventType, Integer quantity) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionType(eventType);
        transactionHistory.setProduct(product);
        transactionHistory.setQuantity(quantity);
        transactionHistoryRepository.save(transactionHistory);
        return transactionMapper.toDTO(transactionHistory);
    }
}
