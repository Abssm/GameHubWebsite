package com.gamehub.repository;

import com.gamehub.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByCategory(String category);
    List<Game> findByFeaturedTrue();
    List<Game> findByTitleContainingIgnoreCase(String title);
    List<Game> findByCategoryIn(List<String> categories);
    
    @Query("SELECT g.category, COUNT(g) FROM Game g GROUP BY g.category")
    List<Object[]> countGamesByCategory();
    
    @Query("SELECT g FROM Game g ORDER BY g.salesCount DESC")
    List<Game> findTopSellingGames();
}