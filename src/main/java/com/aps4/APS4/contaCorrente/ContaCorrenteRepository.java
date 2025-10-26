package com.aps4.APS4.contaCorrente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, String> {
    public ContaCorrente findByConta(String conta);
}