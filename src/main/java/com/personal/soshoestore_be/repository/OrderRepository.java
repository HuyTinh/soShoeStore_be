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

    @Query(value = "Declare @ListMonth\n" +
            "            TABLE (\n" +
            "                MonthNumber INT,\n" +
            "                MonthName VARCHAR(20)\n" +
            "            )\n" +
            "            INSERT INTO\n" +
            "                @ListMonth\n" +
            "            VALUES (1, 'January'),\n" +
            "                (2, 'February'),\n" +
            "                (3, 'March'),\n" +
            "                (4, 'April'),\n" +
            "                (5, 'May'),\n" +
            "                (6, 'June'),\n" +
            "                (7, 'July'),\n" +
            "                (8, 'August'),\n" +
            "                (9, 'September'),\n" +
            "                (10, 'October'),\n" +
            "                (11, 'November'),\n" +
            "                (12, 'December')\n" +
            "select MonthName, (select ISNULL(SUM(total_money),0) from orders WHERE YEAR(order_date) = YEAR(GETDATE()) AND MONTH(order_date) = MonthNumber) as MonthSales from @ListMonth ORDER BY MonthNumber ASC", nativeQuery = true)
    List<YearSales> getYearSales();


}
