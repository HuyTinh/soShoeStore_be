package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.Order;
import com.personal.soshoestore_be.model.YearSales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    Page<Order> findByUserId(Long userId, Pageable pageable);

    @Query(value = """
            Create temporary table tmp_ListMonth                                                                                                                                                                                                                             (
                MonthNumber INT,
                MonthName VARCHAR(20)
            )
                INSERT INTO
                tmp_ListMonth
                VALUES (1, 'January'),
                (2, 'February'),
                (3, 'March'),
                (4, 'April'),
                (5, 'May'),
                (6, 'June'),
                (7, 'July'),
                (8, 'August'),
                (9, 'September'),
                (10, 'October'),
                (11, 'November'),
                (12, 'December');
            select MonthName, (select COALESCE(SUM(total_money),0) from orders WHERE EXTRACT(YEAR FROM order_date) = EXTRACT(YEAR FROM NOW()) AND EXTRACT(MONTH FROM order_date) = MonthNumber) as MonthSales from tmp_ListMonth ORDER BY MonthNumber ASC""", nativeQuery = true)
    List<YearSales> getYearSales();


}
