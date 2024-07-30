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
            Declare @ListMonth
            TABLE (
                MonthNumber INT,
                MonthName VARCHAR(20)
            )
            INSERT INTO
                @ListMonth
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
                (12, 'December')
            SELECT MonthName , ISNULL(
                    (
                        select SUM(price)
                        from order_details
                        where
                            order_id in (
                                select order_id
                                from orders
                                where
                                    month("orders"."order_date") = MonthNumber
                                    and year("orders"."order_date") = :year
                                    and status like 'APPROVE'\
                            )
                            AND shoe_id = :shoeId
                        GROUP BY
                            shoe_id
                    ), 0
                ) as MonthSales
            FROM @ListMonth
            """, nativeQuery = true)
    List<YearSales> findShoeYearSales(int year, int shoeId);


    @Query(value = """
            DECLARE @RepMonth as datetime\s
            SET @RepMonth = CONCAT(FORMAT(?2,'00','en-US'),'/01/', ?1);
            WITH DayList (DayDate) AS\s
            (SELECT @RepMonth
            UNION ALL
            SELECT DATEADD(d, 1, DayDate)
            FROM DayList
            WHERE (DayDate < DATEADD(d, -1, DATEADD(m, 1, @RepMonth))))
            select FORMAT(DayDate, 'yyyy-MM-dd') as 'date' ,(SELECT ISNULL(SUM(total_money), 0) FROM orders WHERE MONTH(order_date) = MONTH(DayDate) AND DAY(order_date) = DAY(DayDate) AND YEAR(order_date) = YEAR(DayDate) AND status LIKE 'APPROVE' AND order_id in\s
            (SELECT order_id FROM order_details WHERE shoe_id = ?3)
            ) as 'date_sales' from DayList""", nativeQuery = true)
    List<MonthSales> findShoeMonthSales(int year, int month, int shoeId);

    @Query(value = "SELECT * FROM shoes WHERE shoe_id IN (SELECT TOP 10 shoe_id FROM order_details GROUP BY shoe_id ORDER BY SUM(total_money) DESC)\n", nativeQuery = true)
    List<Shoe> getCurrentShoesMustHave();

    @Query(value = """
            DECLARE @shoe_categories TABLE(
                title varchar(30) NOT NULL
            )

            INSERT INTO @shoe_categories (title)
            SELECT DISTINCT SUBSTRING(name, 0, (PATINDEX('% %', name))) FROM shoes

            SELECT title, (SELECT TOP 1 image_url FROM shoes WHERE name LIKE CONCAT(title, '%')) FROM @shoe_categories""", nativeQuery = true)
    List<Object[]> getShoesCategories();

    @Query(value = """
            DECLARE @shoe_categories TABLE(
                        title varchar(30) NOT NULL
                        )
                       \s
                        INSERT INTO @shoe_categories (title)
                        SELECT DISTINCT SUBSTRING(name, 0, (PATINDEX('% %', name))) FROM shoes
                        SELECT title, (SELECT SUM(total_money)FROM order_details od LEFT JOIN shoes s ON s.shoe_id = od.shoe_id WHERE name LIKE CONCAT(title, '%') AND od.order_id in (SELECT order_id FROM orders WHERE YEAR(order_date) = YEAR(GETDATE()))) as sales FROM @shoe_categories""", nativeQuery = true)
    List<ShoeCategoryYearSales> getShoeCategoryYearSales();

    @Query(value = """
            DECLARE @shoe_sales TABLE(\s
                          shoe_id INT NOT NULL,\s
                          sales FLOAT NOT NULL,
                          order_count INT NOT NULL
                        )\s
                        \s
                        INSERT INTO  @shoe_sales \s
                         SELECT TOP 10 shoe_id, SUM(total_money), COUNT(shoe_id) FROM order_details WHERE order_id IN (SELECT order_id FROM orders WHERE YEAR(order_date) = YEAR(GETDATE()) AND MONTH(order_date) = ?1) GROUP BY shoe_id ORDER BY COUNT(shoe_id) DESC\s
                         \s
                        SELECT s.*, ss.sales, ss.order_count FROM @shoe_sales ss LEFT JOIN shoes s on ss.shoe_id = s.shoe_id""", nativeQuery = true)
    List<TopShoeSalesInMonth> getTopShoesSalesInMonth(int month);
}