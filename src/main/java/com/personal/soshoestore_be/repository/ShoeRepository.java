package com.personal.soshoestore_be.repository;

import com.personal.soshoestore_be.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {

    @Query("SELECT s FROM Shoe s WHERE s.name = :name")
    List<Shoe> findByName(String name);

    @Query("SELECT s FROM Shoe s WHERE s.name LIKE %:name%")
    Page<Shoe> findByName(String name, Pageable pageable);

    @Query(value = """
            Create temporary table tmp_ListMonth
                   (
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
                   SELECT MonthName , COALESCE(
                           (
                               select SUM(price)
                               from order_details
                               where
                                   order_id in (
                                       select order_id
                                       from orders
                                       where
                                           extract(month from orders.order_date) = MonthNumber
                                           and extract(year from orders.order_date) = :year
                                           and status like 'APPROVE'
                                   )
                                   AND shoe_id = :shoeId
                               GROUP BY
                                   shoe_id
                           ), 0
                       ) as MonthSales
                   FROM tmp_ListMonth
            """, nativeQuery = true)
    List<YearSales> findShoeYearSales(int year, int shoeId);


    @Query(value = """
            DECLARE @RepMonth as datetimes
            SET @RepMonth = CONCAT(FORMAT(?2,'00','en-US'),'/01/', ?1);
            WITH DayList (DayDate) ASs
            (SELECT @RepMonth
            UNION ALL
            SELECT DATEADD(d, 1, DayDate)
            FROM DayList
            WHERE (DayDate < DATEADD(d, -1, DATEADD(m, 1, @RepMonth))))
            select FORMAT(DayDate, 'yyyy-MM-dd') as 'date' ,(SELECT ISNULL(SUM(total_money), 0) FROM orders WHERE MONTH(order_date) = MONTH(DayDate) AND DAY(order_date) = DAY(DayDate) AND YEAR(order_date) = YEAR(DayDate) AND status LIKE 'APPROVE' AND order_id ins
            (SELECT order_id FROM order_details WHERE shoe_id = ?3)
            ) as 'date_sales' from DayList""", nativeQuery = true)
    List<MonthSales> findShoeMonthSales(int year, int month, int shoeId);

    @Query(value = "SELECT * FROM shoes WHERE shoe_id IN (SELECT TOP 10 shoe_id FROM order_details GROUP BY shoe_id ORDER BY SUM(total_money) DESC)\n", nativeQuery = true)
    List<Shoe> getCurrentShoesMustHave();

    @Query(value = """
            CREATE TEMPORARY TABLE tmp_shoe_categories (
                          title varchar(30) NOT NULL
                      )
          
                      INSERT INTO tmp_shoe_categories (title)
                      SELECT DISTINCT SUBSTRING(name, 0, (PATINDEX('% %', name))) FROM shoes;
          
                      SELECT title, (SELECT image_url FROM shoes WHERE name LIKE CONCAT(title, '%')
            LIMIT 1) FROM tmp_shoe_categories""", nativeQuery = true)
    List<Object[]> getShoesCategories();

    @Query(value = """
        CREATE TEMPORARY TABLE tmp_shoe_categories (
            title varchar(30) NOT NULL
        )
        INSERT INTO tmp_shoe_categories (title)
        SELECT DISTINCT SUBSTRING(name, 0, (PATINDEX('% %', name))) FROM shoes;
        SELECT title, (SELECT SUM(total_money)FROM order_details od LEFT JOIN shoes s ON s.shoe_id = od.shoe_id WHERE name LIKE CONCAT(title, '%') AND od.order_id in (SELECT order_id FROM orders WHERE EXTRACT(YEAR FROM order_date) = EXTRACT(YEAR FROM NOW()))) as sales FROM tmp_shoe_categories""", nativeQuery = true)
    List<ShoeCategoryYearSales> getShoeCategoryYearSales();

    @Query(value = """
            CREATE TEMPORARY TABLE tmp_shoe_sales (
              shoe_id INT NOT NULL,
              sales FLOAT NOT NULL,
              order_count INT NOT NULL
            )
            INSERT INTO  tmp_shoe_sales s
             SELECT shoe_id, SUM(total_money), COUNT(shoe_id) FROM order_details WHERE order_id IN (SELECT order_id FROM orders WHERE EXTRACT(YEAR FROM order_date) = EXTRACT(YEAR FROM NOW()) AND EXTRACT(MONTH FROM order_date) = ?1) GROUP BY shoe_id ORDER BY COUNT(shoe_id)
             LIMIT 10 DESC
            SELECT s.*, ss.sales, ss.order_count FROM tmp_shoe_sales ss LEFT JOIN shoes s on ss.shoe_id = s.shoe_id""", nativeQuery = true)
    List<TopShoeSalesInMonth> getTopShoesSalesInMonth(int month);
}