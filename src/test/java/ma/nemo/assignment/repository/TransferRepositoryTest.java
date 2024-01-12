package ma.nemo.assignment.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TransferRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void findOne() {
  }

  @Test
  public void findAll() {

  }

  @Test
  public void save() {
  }

  @Test
  public void delete() {
  }
}