package example.services;

import org.springframework.stereotype.*;

@Service
public class TestService {
  public Integer something(Integer x) {
    return x * x;
  }
}
